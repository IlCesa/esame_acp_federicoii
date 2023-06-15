var main = function () {

    $button = $("#btnRegistra");
    $button.on("click", function (e) {
        e.preventDefault();
        $nome = $('#nome').val();
        $cognome = $('#cognome').val();
        $nascita = $('#nascita').val();
        $cf = $('#cf').val();
        $credito = $('#credito').val();
        //il controllo degli errori viene effettuato sia frontend che backend
        var inputCheck = $nome.trim() === "" || $cognome.trim() === "" || $nascita.trim() === "" || $cf.trim() === "" || $credito.trim() === "";
        if (!inputCheck) {
            //se il controllo viene superato allora chiamo l'api rest per la registrazione dell'utente
            //il bridge è effettuato tramite una richiesta ajax
            var data = { "nome": $nome, "cognome": $cognome, "nascita": $nascita, "cf": $cf, "credito": $credito };
            console.log(data)
            $.ajax({
                url: 'v1/user', type: 'POST', data: data, dataType: 'json',
                success: function (result) {
                    console.log(result)
                    if (result.success) {

                        let badge = result._id
                        Swal.fire(
                            'Registrazione Effettuata con Successo!',
                            'Badge:' + badge,
                            'success'
                        ).then(function (res) {
                            $('#form_registrazione').trigger("reset");
                        });

                    } else {
                        Swal.fire(
                            'Registrazione fallita! Parametri probabilmente non validi',
                            result.msg,
                            'error'
                        )
                    }

                }
            });

        } else {
            //segnalo tramite alert che ci sono errori nell'inserimento dei dati
            Swal.fire(
                'Controlla i dati inseriti!',
                '',
                'error'
            )

        }

    });


}

$(document).ready(function () {

    main();

});
