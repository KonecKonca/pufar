$(document).ready(function () {

    $("#loginForm").validate({

        rules:{

            login:{
                required: true,
                minlength: 5,
                maxlength: 22
            },

            password:{
                required: true,
                minlength: 6,
                maxlength: 20
            }

        },

        // change error message
        // messages:{
        //
        //     login:{
        //         required: "..."
        //     },
        //
        //     password:{
        //         required: "..."
        //     }
        //
        // }

    });

});