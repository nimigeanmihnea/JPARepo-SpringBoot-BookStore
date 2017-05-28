/**
 * Created by Mihnea on 28/05/2017.
 */

(function poll() {
    setTimeout(function() {
        $.ajax({
            url: "/home/polling",
            type: "POST",
            success: function() {
                alert("Your have orders that need your attention");
            },
            dataType: "json",
            complete: poll,
            timeout: 2000
        })
    }, 5000);
})();