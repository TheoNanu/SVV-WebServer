var counter = 0;

function countUp(){
    var text = document.getElementById("counter-text")
    counter++
    text.innerHTML = counter.toString()
    document.getElementById("count-down").style.visibility = "visible"
}

function countDown(){
    var text = document.getElementById("counter-text")
    counter--
    text.innerHTML = counter.toString()
}