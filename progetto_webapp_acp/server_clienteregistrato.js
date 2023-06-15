const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const mongoose = require("mongoose");
mongoose.set('strictQuery', false);
// Middleware per il parsing del corpo delle richieste
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(express.static(__dirname + "/cliente_registrato"));
const router = express.Router();
mongoose.connect('mongodb://localhost/stazione');

var UserSchema = mongoose.Schema({
    nome: String,
    cognome: String,
    nascita: Date,
    cf: String,
    credito: Number,
    prenotazioni: Array,
    sostituzioni: Array
});

var User = mongoose.model("User", UserSchema);

var VeicoliSchema = mongoose.Schema({
    codice: String,
    marca: String,
    modello: String,
    fornitore: String,
    batterie: Array
});

var Veicoli = mongoose.model("Veicles", VeicoliSchema);


//rotta per prelevare tutta la collection veicoli con le batterie associate
router.get("/veicoli", function (req, res) {
    //seleziona solo i documenti dove ci sono almeno una batteria nuova o usata
    Veicoli.find({ $or: [{ batterie: { $elemMatch: { stato: "NUOVO" } } }, { batterie: { $elemMatch: { stato: "USATO" } } }] }, function (err, result) {
        res.json(result);
    });
});

//rotta per aggiornare il credito di un utente
router.put("/user/:_id/credito", function (req, res) {

    try {
        var idUser = mongoose.Types.ObjectId(req.params._id);
    } catch (e) {
        res.json({ msg: "Not valid ID.", success: false, code: 20 });
        return;
    }
    User.findOneAndUpdate({ "_id": req.params._id }, { "credito": req.body.credito }, function (err, user) {
        if (user === null) { res.json({ msg: "User not found.", success: false, code: 20 }); }
        else if (err !== null) {
            res.json({ msg: "Generic error.", success: false, code: 20 });
        } else res.json({ msg: "Balance Updated.", success: true, code: 1 });
    });
});

//rotta per prenotare una batteria, nell'uri bisogna passare l'id utente e l'id della batteria da prenotare.
router.post("/user/:_idUser/prenota/:_idBatteria", function (req, res) {
    console.log(req.params._idBatteria);
    console.log(req.params._idUser);
    try {
        var idBatteria = mongoose.Types.ObjectId(req.params._idBatteria);
        var idUser = mongoose.Types.ObjectId(req.params._idUser);
    } catch (e) {
        res.json({ msg: "Not valid ID.", success: false, code: 20 });
        return;
    }

    const prenotazione = { "_idBatteria": idBatteria, timestamp: Date.now() };

    //classico callback hell, ma non so usare bene mongo quindi meglio di questo per ora non so fare

    User.findOne({ _id: idUser }, function (err, elem) {
        if (err !== null) {
            res.json({ msg: "Generic error.", success: false, code: 20 });
            return;
        } else if (elem === null) {
            res.json({ msg: "User not found.", success: false, code: 20 });
            return;
        }
        var credito = elem.credito
        console.log(credito);
        Veicoli.findOne({ batterie: { $elemMatch: { _id: idBatteria } } }, { "batterie.$": 1 }, function (err, veicolo) {
            console.log(veicolo);
            if (veicolo === null || err !== null) {
                res.json({ msg: "Generic error.", success: false, code: 20 });
                return;
            }
            var costoBatteria = veicolo.batterie[0].costo;
            console.log(costoBatteria)
            if (credito >= costoBatteria) {
                const nuovoCredito = credito - costoBatteria;
                User.updateOne({ _id: idUser }, { $set: { credito: nuovoCredito }, $push: { prenotazioni: prenotazione } }, function (err, el) {
                    /*Veicoli.findOne({batterie: {$elemMatch: {_id:idBatteria}}}, { "batterie.$": 1 }, function (err, veicolo) {
                        //res.json(veicolo);
                        console.log(veicolo);
                    });*/

                    if (err !== null) {
                        res.json({ msg: "Generic error.", success: false, code: 20 });
                        return;
                    }

                    Veicoli.updateOne({ "batterie._id": idBatteria }, { $set: { "batterie.$.stato": "VENDUTO", "batterie.$.acquirente": idUser } }, function (err, veicolo) {
                        if (err !== null) {
                            res.json({ msg: "Generic error.", success: false, code: 20 });
                            return;
                        } else {
                            res.json({ success: true, msg: "Battery reserved.", code: 1 });
                        }


                        //qui t'appost
                    });
                });
            } else {
                res.json({ msg: "Not enough credit.", success: false, code: 20 });
            }
        });

    });

});


/*router.all("*", function(req, res){
    res.status(404).send("Route not available.");
});*/

app.use('/v1', router);

app.listen(3002, () => {
    console.log('Server avviato sulla porta 3002');
});