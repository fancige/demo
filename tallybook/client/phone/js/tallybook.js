var host = "http://fancige.com/";

function hint(msg, duration, timeout) {
    if (!timeout) {
        timeout = 0;
    }
    if(!duration){
        duration = 1000;
    }
    setTimeout(function() {
        appcan.window.openToast({
            msg : msg,
            duration : duration,
            position : 5,
            type : 0
        });
    }, timeout);
}