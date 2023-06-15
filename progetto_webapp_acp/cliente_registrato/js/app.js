var func = function () {
    console.log("here");
}

var main = function () {
    var veicoli = [];
    //carico la lista dei veicoli nella select
    $.ajax({
        url: 'v1/veicoli', type: 'GET',
        success: function (result) {
            result.forEach(function (element, i) {
                //console.log(element.marca + " " + element.modello);
                veicoli.push({ _id: element._id, marca: element.marca, modello: element.modello, batterie: element.batterie });
                $('#selectVeicoli').append($('<option>', {
                    value: i,
                    text: element.marca + " " + element.modello
                }));
            });

            $('#selectVeicoli').trigger('change');


        }
    });

    //in base al valore selezionato nella select si aggiornera' la tabella con la lista di batterie prenotabili
    $('#selectVeicoli').on('change', function () {
        var rightBatterie = veicoli[this.value].batterie;

        $('.table tbody').empty();
        rightBatterie.forEach(function (element, i) {
            $riga = $('<tr>');

            $indice = $('<td>').append(i);
            $id = $('<td>').append(element._id);
            $stato = $('<td>').append(element.stato);
            $cicli = $('<td>').append(element.cicli_ricariche);
            $costo = $('<td>').append(element.costo);

            $riga.append($indice);
            $riga.append($id);
            $riga.append($stato);
            $riga.append($cicli);
            $riga.append($costo);
            $button = $('<button class="btn btn-primary" class="btnPrenota" batteriaID=' + element._id + '>Prenota</button>');
            $riga.append($button);
            if (element.stato !== "VENDUTO") $('.table tbody').append($riga);


            //nel momento in cui viene premuto il button "prenota" viene prelevato il badge dal dom
            //e tramite una richiesta ajax dove nei parametri ci sono il badgeid e la batteryID
            //la batteria' sara' prenotata SE il cliente ha abbastanza credito
            $button.on("click", function (e) {
                e.preventDefault();
                userID = $(e.target).attr("batteriaID");
                badgeID = $('#inputBadgeIDPrenotazione').val();
                console.log(badgeID);


                if (badgeID.trim() === '') {
                    Swal.fire(
                        'Controlla i dati inseriti!',
                        '',
                        'error'
                    )
                } else {

                    //bridge
                    $.ajax({
                        url: 'v1/user/' + badgeID + '/prenota/' + userID, type: 'POST', dataType: 'json',
                        success: function (result) {
                            console.log(result);
                            if (result.success) {
                                Swal.fire(
                                    'Prenotazione effettuata con sucesso!',
                                    result.msg,
                                    'success'
                                ).then(function (result) {

                                    if (result.isConfirmed) {
                                        $('#inputBadgeIDPrenotazione').val("");
                                        location.reload();
                                    }

                                });
                            } else {
                                Swal.fire(
                                    'Prenotazione non effettuata!',
                                    result.msg,
                                    'error'
                                )
                            }


                        }
                    });


                }




            });





        });





    });



    //caso d'uso ricaricaCredito
    //in questo caso il credito è direttamente aggiornato e non ricaricato
    //dalla ui bisogna passare il badgeID ed il nuovo credito utente
    //l'idea è quella di simulare un payment-gateway dove confermato il pagamento da parte del servizio
    //questa rotta risulta essere una hook, quindi da chiamare solo dopo che il pagamento è stato confermato.
    //qui invece la chiamiamo noi a manella dal dom ma è concettualmente sbagliato
    $buttonRicarica = $("#btnRicarica");
    $buttonRicarica.on("click", function (e) {
        e.preventDefault();
        $badge = $('#inputBadgeID').val();
        $credito = $('#inputCredito').val();
        var inputCheck = $badge.trim() === "" || $credito.trim() === "";
        if (!inputCheck) {

            var data = { "credito": $credito };
            $.ajax({
                url: 'v1/user/' + $badge + '/credito', type: 'PUT', data: data, dataType: 'json',
                success: function (result) {
                    console.log(result)
                    if (result.success) {
                        let badge = result._id

                        Swal.fire(
                            'Credito aggiornato!',
                            result.msg,
                            'success'
                        ).then(function (res) {
                            $('#form_registrazione').trigger("reset");
                        });
                    } else {
                        Swal.fire(
                            'Credito non aggiornato!',
                            result.msg,
                            'error'
                        )
                    }
                }
            });

        } else {

            Swal.fire(
                'Controlla i dati inseriti!',
                '',
                'error'
            )

        }

    });


}

$(document).ready(function () {

    main();// Call the main function

});