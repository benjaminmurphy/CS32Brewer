function allowDrop(event) {
    event.preventDefault();
}

function drag(event) {
    event.dataTransfer.setData("text", event.target.id);
}

function drop(event) {
    event.preventDefault();
    var data = event.dataTransfer.getData("text");
    var element = document.getElementById(data);
    element.style.display = "block";
    event.target.appendChild(element);
}

function trash(event) {
    event.preventDefault();
    var data = event.dataTransfer.getData("text");
    var el = document.getElementById(data);
    el.parentNode.removeChild(el);
}

function dragCopy(event) {
    var original = document.getElementById(event.target.id);
    var copy = original.cloneNode(false);
    copy.id = "copy";
    copy.style.display = "none";
    original.appendChild(copy);
    
    event.dataTransfer.setData("text", "copy");
}