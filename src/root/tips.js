var firstPhrase = document.getElementById('first').innerHTML

var firstPhraseCopy = firstPhrase

var splitted

function changeContent(textId){
    var target = document.getElementById(textId)
    var textToDisplay = ""
    var notSplitted

    splitted = target.innerHTML.split(",")

    console.log(splitted)
    console.log(firstPhrase)
    
    if(splitted.length == 1)
    {
        textToDisplay = firstPhrase
        firstPhraseCopy = firstPhrase
    }
    else
    {
        for(let i = 0; i < splitted.length - 1; i++)
        {
            textToDisplay += splitted[i]
            if(i != splitted.length - 2)
                textToDisplay += ","
        }

        firstPhraseCopy = textToDisplay
    }
    console.log(firstPhraseCopy)

    target.innerHTML = textToDisplay

}