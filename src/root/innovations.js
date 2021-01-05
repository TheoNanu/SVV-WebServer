var randomTextList = ["Eleifend ornare cubilia justo dictum ligula odio quisque senectus aptent",
                      "Curae vehicula pharetra massa primis lobortis venenatis vehicula lacus, quisque litora amet diam accumsan himenaeos",
                      "Nam libero tristique himenaeos phasellus lacus",
                      "Neque laoreet pulvinar diam adipiscing turpis donec amet mi ultricies ligula amet venenatis orci",
                      "Est suscipit aliquam mattis urna etiam",
                      "Curabitur lorem interdum praesent massa turpis risus phasellus ante integer et sem",
                      "Pretium platea dapibus posuere amet primis cubilia malesuada aenean ipsum metus eleifend",
                      "Sit ac nisl suscipit lacinia diam justo odio",
                      "Mattis posuere quisque urna vestibulum tempor nulla quisque proin ante netus"]

var i = 0
var j = 0
var t = 0

function changeText(textId, button) {
    var text = document.getElementById(textId)
    if(button === "first")
    {
        text.innerHTML = randomTextList[i % 3]
        i++
    }
    else if(button === "second")
    {
        text.innerHTML = randomTextList[(j % 3) + 3]
        j++
    }
    else
    {
        text.innerHTML = randomTextList[(t % 3) + 6]
        t++
    }
}

function hideTextAndButton(textId, buttonId){
    var title = document.getElementById(textId)
    //title.hidden = "true"
    title.remove()
    var button = document.getElementById(buttonId)
    button.remove()
}