// Notification Module
var Notification = (function ($) {
    // Function to notify success
    function notifySuccess(title, message = '') {
        // Combine title and message into a single HTML string
        var notificationMessage = `<h4>${title}</h4><p>${message}</p>`;

        // Call bootstrapGrowl with the combined message
        $.bootstrapGrowl(notificationMessage, {
            type: 'success', // Change to the desired growl type
            delay: 2500,
            allow_dismiss: true
        });
    }

    // Function to notify info
    function notifyInfo(title, message = '') {
        // Combine title and message into a single HTML string
        var notificationMessage = `<h4>${title}</h4><p>${message}</p>`;

        // Call bootstrapGrowl with the combined message
        $.bootstrapGrowl(notificationMessage, {
            ele: '#notification-container', // Specify the container to append to
            type: 'info', // Change to the desired growl type
            delay: 2500,
            allow_dismiss: true
        });
    }

    // Function to notify error
    function notifyError(title, message = '') {
        // Combine title and message into a single HTML string
        var notificationMessage = `<h4>${title}</h4><p>${message}</p>`;

        // Call bootstrapGrowl with the combined message
        $.bootstrapGrowl(notificationMessage, {
            type: 'danger', // Change to the desired growl type
            delay: 2500,
            allow_dismiss: true
        });
    }


    // Function to unescape Unicode characters
    function unescapeUnicode(str) {
        return str.replace(/\\u([a-fA-F0-9]{4})/g, function (g, m1) {
            return String.fromCharCode(parseInt(m1, 16));
        });
    }

    // Expose the notification methods
    return {
        notifySuccess: notifySuccess,
        notifyInfo: notifyInfo,
        notifyError: notifyError,
        unescapeUnicode: unescapeUnicode
    };
})(jQuery);
