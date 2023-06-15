const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const mongoose = require("mongoose");
mongoose.set('strictQuery', false);
// Middleware per il parsing del corpo delle richieste
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(express.static(__dirname + "/gestore_stazione"));
const router = express.Router();

mongoose.connect('mongodb://localhost/stazione');

var VeicoliSchema = mongoose.Schema({
    codice: String,
    marca: String,
    modello: String,
    fornitore: String,
    batterie: Array
});

var Veicoli = mongoose.model("Veicles", VeicoliSchema);

router.get("/veicles", function (req, res) {

    Veicoli.find({}, function (err, result) {
        if (err !== null) res.json({ msg: "Generic error.", success: false, code: 20 });
        else res.json({ msg: "", success: true, code: 1, data: result });
    });


});

//visualizza una singola batteria dato l'id
router.get("/batterie/:_id", function (req, res) {
    var idBatteria = mongoose.Types.ObjectId(req.params._id);
    Veicoli.findOne({ batterie: { $elemMatch: { _id: idBatteria } } }, { "batterie.$": 1 }, function (err, result) {
        if (err !== null) res.json({ msg: "Generic error.", success: false, code: 20 });
        else res.json(result);
    });
});

//carica una batteria
router.post("/batteria", function (req, res) {
    console.log(req.body);
    const { costo, cicli_ricariche, codice_autovettura, stato } = req.body;

    wrongInput = isNaN(costo) || isNaN(cicli_ricariche) || costo.trim() == "" || cicli_ricariche.trim() == "" || codice_autovettura.trim() == "" || stato.trim() == "" || !(stato.trim() == "NUOVO" || stato.trim() == "USATO" || stato.trim() == "VENDUTO");
    if (wrongInput) {
        res.json({ success: false, code: 20, msg: "Wrong Params." });
        return;
    }


    var objectId = new mongoose.mongo.ObjectID();
    const batteria = { "_id": objectId, "stato": stato, "costo": costo, cicli_ricariche: cicli_ricariche };

    //var newOrder = new Batteria({"stato":stato, "costo":costo,codice_autovettura:codice_autovettura,  cicli_ricariche:cicli_ricariche});

    Veicoli.findOneAndUpdate({ _id: codice_autovettura }, { $push: { batterie: batteria } }, function (error, success) {
        if (error) {
            //console.log(error);
            res.json({ msg: "Generic error.", success: false, code: 20, error: error });
        } else {
            console.log(objectId);
            res.json({ _id: objectId, msg: "Battery saved.", success: true, code: 1 });
        }
    });
});

//rotta specifica per aggiornare lo stato di una batteria
router.put("/batteria", function (req, res) {
    console.log(req.body);
    const { _id, stato } = req.body;
    wrongInput = _id.trim() == "" || stato.trim() == "" || !(stato === "NUOVO" || stato === "VENDUTO" || stato === "USATO");
    if (wrongInput) {
        res.json({ success: false, code: 20, msg: "Wrong Params." });
        return;
    }
    try {
        batteryID = mongoose.Types.ObjectId(_id)
    } catch (e) {
        res.json({ msg: "Not valid ID.", success: false, code: 20 });
        return;
    }

    console.log(batteryID);
    Veicoli.updateOne({ "batterie._id": batteryID }, { $set: { "batterie.$.stato": stato } }, function (err, result) {
        console.log(result);
        if (err !== null) res.json({ msg: "Generic error.", success: false, code: 20 });
        else if (result.matchedCount === 0) { res.json({ msg: "Battery not found.", success: false, code: 20 }); }
        else res.json({ msg: "Battery STATE Updated.", success: true, code: 1 });
        //qui t'appost
    });
});

//uso il router express per il versioning
app.use('/v1', router);

// Avvio del server
app.listen(3000, () => {
    console.log('Server avviato sulla porta 3000');
});