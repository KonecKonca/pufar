$(document).ready(function () {
        $('#newPassword1').change(function () {
            var password1 = $('#newPassword1').val();
            var password2 = $('#newPassword2').val();

            $.ajax({
                type:'POST',
                data: {
                    password1 : password1,
                    password2 : password2,
                    commandResponse : 'CHECK_PASSWORDS'
                },
                url:'/pufar',

                success: function (result) {
                    document.getElementById("isPasswordEquals1").textContent=result;
                    document.getElementById("isPasswordEquals2").textContent=result;
                }

            });

        });
    });

$(document).ready(function () {
    $('#newPassword2').change(function () {
        var password1 = $('#newPassword1').val();
        var password2 = $('#newPassword2').val();

        $.ajax({
            type:'POST',
            data: {
                password1 : password1,
                password2 : password2,
                commandResponse : 'CHECK_PASSWORDS'
            },
            url:'/pufar',
            success: function (result) {
                document.getElementById("isPasswordEquals1").textContent=result;
                document.getElementById("isPasswordEquals2").textContent=result;
            }

        });
    });

});
