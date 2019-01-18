$(document).ready(function () {
    $('#inputLogin').change(function () {
        var login = $('#inputLogin').val();

        // alert("3r3r3r3" + login);

        $.ajax({
            type:'POST',
            data: {
                login : login,
                commandResponse : 'CHECK_LOGIN'
            },
            url:'/pufar',

            success: function (result) {
                document.getElementById("isLoginQnique").textContent=result;
            }

        });
    });

});