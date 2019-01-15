$(document).ready(function () {

    customErrorMessage.oninvalid = function (event) {
        event.target.setCustomValidity('XXXXX');
    };

});