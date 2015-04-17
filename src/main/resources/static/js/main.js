var copynum = 0;

var playground = document.getElementById("playground");
var menu = document.getElementById("menu");
var consoleBox = document.getElementById("console");

var droppableWidth = 100;

var lineNumber = 0;

var socket = null;

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

    if (playground.contains(original)) {

        copy = original.cloneNode(true);
        /*copy.id = "copy_" + copynum.toString();
        copynum += 1;
        copy.style.display = "none";
        original.appendChild(copy);*/

        if (original.classList.contains("shrinkable")) {
            original.classList.remove("shrinkable");
            var parent = original.parentNode;
            //parent.removeChild(original);
            shrinkParentsAndThis(parent);
        }

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
        currentElement.resize([]);
        currentElement = currentElement.parentNode;
    }
}

function shrinkParentsAndThis(element) {
    var currentElement = element;
    while (currentElement.id !== "playground" && currentElement.id !== "menu") {
        currentElement.resize([element]);
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


$(".typeSelect").bind("change", function(event){
  var myType = this.options[this.selectedIndex].value;
  var par = this.parentNode;
  if ($(par).hasClass("literal")) {
  par.getElementsByClassName("litVal")[0].remove();
    var newField;
    if (myType == "number") {
        newField = document.createElement('input');
        $(newField).addClass("litVal");
        newField.type = "number";
        newField.placeholder ="-0.0";
    } else if (myType == "string") {
        newField = document.createElement('input');
        $(newField).addClass("litVal");
        newField.type = "text";
        newField.placeholder ="Text";
    } else if (myType == "bool") {
        newField = document.createElement('input');
        $(newField).addClass("litVal");
        newField.type = "number";
        newField.placeholder ="-0.0";
    }
    par.appendChild(newField);
  }
  $(par).attr("valType", myType);
});

HTMLDivElement.prototype.getValue = function() {
    return this.getElementsByClassName("litVal")[0].value;
}



var itemPadding = 10;
var minHeight = 40;
var minWidth = 100;

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
    if (newHeight > minHeight) {
        this.style.height = newHeight + 'px';
    } else {
        this.style.height = minHeight + 'px';
    }
    if (newWidth > minWidth) {
        this.style.width = newWidth + 'px';
    } else {
        this.style.width = minWidth + 'px';
    }
}

function setConsole() {
    if (consoleBox.style.display == "none") {
        consoleBox.style.display = "block";
        playground.style.width = "calc(80% - 300px)";
    } else {
        consoleBox.style.display = "none";
        playground.style.width = "calc(100% - 300px)";
    }
}

var programRunning = false;
function runProgram() {
    if (consoleBox.style.display == "none") {
        setConsole();
    }
    if (programRunning) {
        alert("Program is already running! Press Kill to cease execution.");
        return 1;
    }

    console.log("Running program!");
    programRunning = true;

    openStream();
}

HTMLDivElement.prototype.getJSONFormat = function() {
    return "This element did not have formatting"
}
/*HTMLDivElement.prototype.getJSONFormat = function() {
    return "This element did not have formatting"
}*/

function killProgram() {
    if (!programRunning) {
        alert("Program is not running! Press Run to start execution.");
        return 1;
    }

    console.log("Killing program!");
    programRunning = false;

    if (socket != null) {
        socket.send("kill");
    }
    
}

function log(msg) {
    var line = document.createElement("p");
    var text = document.createTextNode(lineNumber.toString() + ": " + msg);
    line.appendChild(text);

    consoleBox.appendChild(line);
}

function openStream() {
    socket = new WebSocket('ws://localhost:2567');

    socket.onopen = function(event) {
        var request = JSON.stringify(compile()); // Convert program and put it in here.
        socket.send(request);
    }

    socket.onmessage = function(event) {
        var message = JSON.parse(event.data);

        if (message.type === "done") {
            socket.close();
            programRunning = false;
        } else if (message.type === "log") {
            log(message.msg);
        } else if (message.type === "error") {
            log(message.msg);
            socket.close();
            programRunning = false;
        }   
    }

    socket.onclose = function(event) {
        log("Execution complete.");
        socket = null;
    }
}

function compile() {
    
    var request = {main : []};
    
    for (var idx = 0; idx < playground.children.length; idx++) {
        request.main.push(playground.children[idx].compile());
    }
    
    return request;
}

HTMLDivElement.prototype.compile = function() {

    var block = {};

    if (this.classList.contains("set")) {

        block.type = "set";

        var firstDropZone = this.children[1];
        var secondDropZone = this.children[2];

        block.name = firstDropZone.getElementsByClassName("var")[0].compile();
        block.value = secondDropZone.children[1].compile();

    } else if (this.classList.contains("get")) {
        block.type = "get";
        
        var dropZone = this.children[1];
        
        block.name = dropZone.getElementsByClassName("var")[0].compile();
        
    } else if (this.classList.contains("var")) {
        block.type = "var";
        
        block.name = this.getElementsByTagName("select")[0].value;
        
        block.class = this.getAttribute("valType");
        
    } else if (this.classList.contains("literal")) {
        block.type = "literal";
        
        block.value = this.getValue();
        
        block.class = this.getAttribute("valType");

    } else if (this.classList.contains("print")) {
        block.type = "print";
        
        var firstDropZone = this.children[1];
        block.name = firstDropZone.getElementsByClassName("var")[0].compile();
        
    } else if (this.classList.contains("arithmetic")) {
        block.type = "numeric_operator";
        
        var firstDropZone = this.children[1];
        var operator = this.children[2];
        var secondDropZone = this.children[3];
        
        block.arg1 = firstDropZone.children[1].compile();
        block.arg2 = secondDropZone.children[1].compile();
        
        block.name = operator.value;
        
    } else if (this.classList.contains("while")) {
        block.type = "while";
        
        var firstDropZone = this.children[1];
        var secondDropZone = this.children[2];
        
        block.condition = firstDropZone.children[1].compile();
        
        block.commands = [];
        
        for (var idx = 0; idx < secondDropZone.children.length; idx++) {
            if (secondDropZone.children[idx].tagName === "DIV") {
                block.commands.push(secondDropZone.children[idx].compile());
            }
        }
        
        
    } else if (this.classList.contains("equality")) {
        block.type = "comparison";
        
        var firstDropZone = this.children[1];
        var operator = this.children[2];
        var secondDropZone = this.children[3];
        
        block.arg1 = firstDropZone.children[1].compile();
        block.arg2 = secondDropZone.children[1].compile();
        
        block.name = operator.value;
    }
    
    return block;
}