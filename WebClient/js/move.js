
window.addEventListener("scroll", function(event) {
    var top = this.scrollY,
        left =this.scrollX;
        var ele = document.getElementById('inner-strip');
          ele.style.top =  -1*top/2+20;


}, false);
