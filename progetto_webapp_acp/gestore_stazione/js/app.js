var main = function () {

    var veicoli = [];
    var batterieEsauste = [];
    //carico la lista di veicoli
    //per ottenere le batterie esauste
    $.ajax({
        url: 'v1/veicles', type: 'GET',
        success: function (result) {
            result.data.forEach(function (element, i) {
                //var element = elements.data;
                //console.log(element.marca + " " + element.modello);
                veicoli.push({ _id: element._id, marca: element.marca, modello: element.modello, batterie: element.batterie });
                $('#autovetture_select').append($('<option>', {
                    value: i,
                    text: element.marca + " " + element.modello
                }));

                var nome_veicolo = element.marca + " " + element.modello;
                //popolo la tabella con le batterie esauste
                element.batterie.forEach(function (element, i) {
                    if (element.cicli_ricariche < 5) {
                        batterieEsauste.push(element);
                        $riga = $('<tr>');

                        $indice = $('<td>').append(i);
                        $id = $('<td>').append(element._id);
                        $stato = $('<td>').append(element.stato);
                        $cicli = $('<td>').append(element.cicli_ricariche);
                        $costo = $('<td>').append(element.costo);

                        $riga.append(nome_veicolo);
                        $riga.append($id);
                        $riga.append($stato);
                        $riga.append($cicli);
                        $riga.append($costo);

                        $('.table tbody').append($riga);
                        
                    }
                });


            });

            console.log(batterieEsauste);
            // $('#autovetture_select').trigger('change'); 
        }
    });

    
    $("#btnCaricaBatteria").on("click", function (e) {
        e.preventDefault();

        
        $stato = $('#stato_select').find(":selected").val();
        $cicli_ricariche = $('#cicli_ricariche').val();
        $costo = $('#costo').val();

        var inputCheck = $stato.trim() === "" || $cicli_ricariche.trim() === "" || $costo.trim() === "";
        if (!inputCheck) {
            
            rightIndex = $('#autovetture_select').find(":selected").val();
            //prendo lìindice selezionato nella select, tramite questo accedo al veicolo corretto e prelevo l'id
            var veicolo = veicoli[rightIndex];

            var batteria = { codice_autovettura: veicolo._id, stato: $stato, cicli_ricariche: $cicli_ricariche, costo: $costo };
            //viene inserita una batteria tramite rest api POST
            $.ajax({
                url: 'v1/batteria', type: 'POST', dataType: 'json', data: batteria,
                success: function (result) {
                    //console.log(result);
                    if (result.success) {
                        //in caso di inserimento andato a buon fine compare un alert di successo
                        Swal.fire(
                            'Inserimento effettuato con successo!',
                            result.msg,
                            'success'
                        ).then(function (result) {

                            if (result.isConfirmed) {
                                //$('#inputBadgeIDPrenotazione').val("");
                                //location.reload();
                                $('#form_inserimentobatterie').trigger("reset");
                            }

                        });
                    } else {
                        //in caso di errori di inserimento backend compare un alert di errore
                        Swal.fire(
                            'Inserimento non effettuato!',
                            result.msg,
                            'error'
                        )
                    }


                }
            });




        }

    });





}



$(document).ready(function () {

    main();

});
