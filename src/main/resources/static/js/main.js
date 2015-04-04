var copynum = 0;

var playground = document.getElementById("playground");
var menu = document.getElementById("menu");
var consoleBox = document.getElementById("console");

var droppableWidth = 100;

// Allows elements to be dropped.

function allowDrop(event) {
    event.preventDefault();
}

// Drop handler.

function drop(event) {

    var target = event.target;
    var data = event.dataTransfer.getData("text");
    var element = document.getElementById(data);

    if (target.classList.contains("droppable") && playground.contains(target)) {
        event.preventDefault();
        element.style.display = "block";
        target.appendChild(element);
        element.classList.add("shrinkable");
        growParents(element);
    } else if (playground.contains(target)) {
        event.preventDefault();
        element.style.display = "block";
        playground.appendChild(element);
    } else if (menu.contains(target)) {
        event.preventDefault();
        element.parentNode.removeChild(element);
    }
}

// Starting drag handler. Copies elements.

function startDrag(event) {
    var original = document.getElementById(event.target.id);
    var copy = null;

    if (original.classList.contains("shrinkable")) {
        original.classList.remove("shrinkable");
        shrinkParents(original);
    }

    if (playground.contains(original)) {
        copy = original;
    } else if (menu.contains(original)) {
        copy = original.cloneNode(true);
        copy.id = "copy_" + copynum.toString();
        copynum += 1;
        copy.style.display = "none";
        original.appendChild(copy);
    }

    event.dataTransfer.setData("text", copy.id);
}

// Grow or shrink all parent elements.

function growParents(element) {
    var currentElement = element.parentNode;
    while (currentElement.id !== "playground" && currentElement.id !== "menu") {
        currentElement.resize();
        currentElement = currentElement.parentNode;
    }
}

function shrinkParents(element) {
    var currentElement = element.parentNode;
    while (currentElement.id !== "playground" && currentElement.id !== "menu") {
        currentElement.resize();
        currentElement = currentElement.parentNode;
    }
}

// Grow or shrink one element.

HTMLDivElement.prototype.grow = function(width, height) {    
    var newHeight = this.offsetHeight + 20;
    this.style.height = newHeight + 'px';
    var newWidth = width;

    if (!this.classList.contains("droppable")) {
        newWidth += this.scrollWidth;
    }

    this.style.width = newWidth + 'px';
}

HTMLDivElement.prototype.shrink = function(width, height) {
    var newHeight = this.scrollHeight - 20;
    this.style.height = newHeight + 'px';
    var newWidth = this.offsetWidth  - width;

    if (!this.classList.contains("droppable")) {
        newWidth += droppableWidth;
    }

    this.style.width = newWidth + 'px';
}


var itemPadding = 10;

HTMLDivElement.prototype.resize = function() {    
    var sumHeight = 0;
    var maxWidth = 0;

    var children = this.children;
    for(var i = 0; i < children.length; i++) {
        console.log(i);
        var child = children[i];
        console.log(child);
        if ((child.classList.contains("droppable") 
            || child.classList.contains("item")
            || child.classList.contains("functionSelect"))
            && child !== this) {

            if (child.offsetWidth > maxWidth) {
                maxWidth = child.offsetWidth;
            }
            sumHeight += child.offsetHeight + itemPadding;
        }
    }

    var newHeight = sumHeight + itemPadding;
    var newWidth = maxWidth 
    if (!this.classList.contains("droppable")) {
        newWidth += itemPadding*2;
        newHeight += itemPadding;
    }
    this.style.height = newHeight + 'px';
    this.style.width = newWidth + 'px';
}

function setConsole() {
    if (consoleBox.style.display == "none") {
        consoleBox.style.display = "block";
        playground.style.width = "calc(80% - 300px)";
    } else {
        consoleBox.style.display = "none";
        playground.style.width = "calc(100% - 300px";
    }
}
var programRunning = false;
function runProgram() {
    if (consoleBox.style.display == "none") {
        setConsole();
    }
    if (programRunning) {
        alert("Program is already running! Press Kill to cease execution");
        return 1;
    }

    console.log("Running program!");
    programRunning = true;



}

HTMLDivElement.prototype.getJSONFormat = function() {
    console.log("This is where we recursively make each json thing");
}

function killProgram() {
    if (!programRunning) {
        alert("Program is not running! Press Run to start execution");
        return 1;
    }

    console.log("Killing program!");
    programRunning = false;
    
}




