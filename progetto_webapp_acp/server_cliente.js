const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const mongoose = require("mongoose");
mongoose.set('strictQuery', false);
// Middleware per il parsing del corpo delle richieste
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(express.static(__dirname + "/cliente"));
const router = express.Router();
mongoose.connect('mongodb://localhost/stazione');

var UserSchema = mongoose.Schema({
    nome: String,
    cognome: String,
    nascita: String,
    cf: String,
    credito: Number,
    prenotazioni: Array,
    sostituzioni: Array
});




var User = mongoose.model("User", UserSchema);

//rotta per la creazione di un utente
router.post("/user", function (req, res) {
    console.log(req.body);
    //il codice badge sara' l'id generato da mongo.
    const { nome, cognome, nascita, cf, credito } = req.body;
    var dateRegex = /^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/;
    console.log("regex " + dateRegex.test(nascita));
    //validazioneInput, uso una regex per la data
    wrongInput = !dateRegex.test(nascita) || nome.trim() == "" || cognome.trim() == "" || nascita.trim() == "" || cf.trim() == "" || credito.trim() == "";
    if (wrongInput) {
        res.json({ success: false, code: 20, msg: "Wrong Params." });
        return;
    }
    var newUser = new User({ "nome": nome, "cognome": cognome, "nascita": nascita, "cf": cf, "credito": credito });

    newUser.save(function (err, result) {
        if (err !== null) {
            console.log("Errore verificato.");
            console.log(err);
            res.json({ success: false, code: 20, msg: "Schema types problems in db" });

        } else {
            console.log("Inserito con successo.");
            res.json({ _id: result._id, msg: "Badge created.", success: true, code: 1 });
        }
    });



});

//rotta per la lettura di un utente dato il suo id
router.get("/user/:_id", function (req, res) {
    User.find({ "_id": req.params._id }, function (err, user) {
        if (err) {
            console.log(err);
            res.json({ msg: "Generic error.", success: false, code: 20 });
        } else {
            if (user.length == 0) {
                res.json({ msg: "User not found.", success: false, code: 20 });
            } else res.json(user);
        }
    });
});

app.use('/v1', router);

app.listen(3001, () => {
    console.log('Server avviato sulla porta 3001');
});