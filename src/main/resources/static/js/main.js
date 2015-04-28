var copynum = 0;

var playground = document.getElementById("playground");
var menu = document.getElementById("menu");
var consoleBox = document.getElementById("console");

var lineNumber = 0;

var logger = null;

var itemPadding = 10;
var minHeight = 40;
var minWidth = 100;

function allowDrop(event) {
    event.preventDefault();
}

// Drop handler.

function drop(event) {

    var target = event.target;
    var data = event.dataTransfer.getData("text");
    var element = document.getElementById(data);

    var parent = element.parentNode;

    if (target.classList.contains("droppable") && playground.contains(target)) {
        event.preventDefault();
        element.style.display = "block";
        target.appendChild(element);
        element.classList.add("shrinkable");
        recursiveResize(element);
    } else if (playground.contains(target)) {
        event.preventDefault();
        element.style.display = "block";
        playground.appendChild(element);
        element.resize();
    } else if (menu.contains(target)) {
        event.preventDefault();
        element.parentNode.removeChild(element);
    }
    if (parent && !menu.contains(parent) && parent !== null && parent.tagName === "DIV") {
        recursiveResize(parent);
    }
}

// Starting drag handler. Copies elements.

function startDrag(event) {
    var original = document.getElementById(event.target.id);
    var parent = original.parentNode;
    var copy = null;

    if (playground.contains(original)) {
        event.dataTransfer.setData("text", original.id);
        recursiveResize(parent);
    } else if (menu.contains(original)) {
        copy = original.cloneNode(true);
        copy.id = "copy_" + copynum.toString();
        copynum += 1;
        copy.style.display = "none";
        original.appendChild(copy);
        event.dataTransfer.setData("text", copy.id);
    }
}

// Grow or shrink all parent elements.

function recursiveResize(element) {
    while (element.id !== "playground" && element.id !== "menu") {
        element.resize();
        element = element.parentNode;
    }
}

HTMLDivElement.prototype.getValue = function() {    
    if (this.classList.contains("nVal")) {
        return parseFloat(this.getElementsByTagName("input")[0].value);
    } else if (this.classList.contains("sVal")) {
        return this.getElementsByTagName("input")[0].value;
    } else if (this.classList.contains("bVal")) {
        if (this.classList.contains("true")) {
            return true;
        } else {
            return false;
        }
    }
}

HTMLDivElement.prototype.resize = function() {    
    var sumHeight = 0;
    var maxWidth = 0;

    if (this.classList.contains("literal") || this.classList.contains("droppable")) {
        minWidth = 100;
    } else if (this.classList.contains("item")) {
        minWidth = 200;
    }

    if (this.classList.contains("droppable") && !this.classList.contains("single")) {
        sumHeight += 15;
    }

    var children = this.children;
    for(var i = 0; i < children.length; i++) {
        var child = children[i];
        sumHeight += child.offsetHeight;

        if (child.offsetWidth > maxWidth && child.classList.contains("droppable") 
            || child.classList.contains("item")
            || child.classList.contains("functionSelect")) {
            maxWidth = child.offsetWidth;
        }

        if (child.tagName === "SELECT" && !this.classList.contains("var")) {
            sumHeight += 15;   
        }

    }

    this.style.height = Math.max(sumHeight, minHeight) + 'px';
    this.style.width = Math.max(maxWidth, minWidth) + 'px';
}

function setConsole() {
    if (consoleBox.style.display == "none") {
        consoleBox.style.display = "block";
        playground.style.width = "calc(80% - 300px);";
    } else {
        consoleBox.style.display = "none";
        playground.style.width = "calc(100% - 300px);";
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

    var requestObj = compileMain();

    console.log(requestObj);

    var request = JSON.stringify(requestObj);

    clearLogs();
    lineNumber = 0;

    $.post("/run", request, function(response) {
        response = JSON.parse(response);

        if (response.status == "failure") {

            console.log(response);
            
            for (var i = 0; i < response.error.length; i++) {
                if (response.error[i].isError) {
                    log(response.error[i].msg, true);
                } else {
                    log(response.error[i].msg, false);
                }            }

            programRunning = false;
        } else {
            logger = setInterval(getLogs, 50);
        }
    })
}

function clearLogs() {

    var toRemove = [];

    for (var i = 0; i < consoleBox.children.length; i++) {
        if (consoleBox.children[i].tagName === "P") {
            toRemove.push(consoleBox.children[i]);
        }
    }

    for (var i = 0; i < toRemove.length; i++) {
        consoleBox.removeChild(toRemove[i]);
    }
}

function getLogs() {
    $.post("/logs", function(response) {
        response = JSON.parse(response);

        for (var i = 0; i < response.messages.length; i++) {
            if (response.messages[i].isError) {
                log(response.messages[i].msg, true);
            } else {
                log(response.messages[i].msg, false);
            }
        }

        if (!response.running) {
            clearInterval(logger);
            programRunning = false;
            log("Execution complete.");
        }
    });
}

function killProgram() {
    $.post("/kill", function(response) {
        response = JSON.parse(response);

        if (response.status == "failure") {
            console.log("Kill failed.");
        } else {
            clearInterval(logger);
            programRunning = false;
            log("Program killed.");
        }
    });
}

function log(msg, isError) {
    var line = document.createElement("p");
    var text = document.createTextNode(lineNumber.toString() + ": " + msg);
    line.appendChild(text);
    
    if (isError) {
        line.className += " errorMsg";
    }
    
    lineNumber += 1;

    consoleBox.appendChild(line);
}

function compileMain() {
    var request = {main : []};
    for (var idx = 0; idx < playground.children.length; idx++) {
        request.main.push(playground.children[idx].compile());
    }
    return request;
}

function compile(element) {
    if (element === null || element === undefined || element.tagName !== "DIV") {
        return null;
    } else {
        return element.compile();
    }
}

HTMLDivElement.prototype.compile = function() {

    var block = {};

    if (this.classList.contains("set")) {

        block.type = "set";

        var firstDropZone = this.children[1];
        var secondDropZone = this.children[3];

        block.name = compile(firstDropZone.children[0]);
        block.value = compile(secondDropZone.children[0]);

    } else if (this.classList.contains("get")) {
        block.type = "get";

        var dropZone = this.children[1];

        block.name = compile(dropZone.children[0]);

    } else if (this.classList.contains("var")) {

        block.type = "var";

        block.class = this.children[1].value;
        block.name = this.children[2].value;

    } else if (this.classList.contains("literal")) {
        block.type = "literal";

        block.value = this.getValue();

        if (this.classList.contains("nVal")) {
            block.class = "number";
        } else if (this.classList.contains("nVal")) {
            block.class = "string";
        } else {
            block.class = "bool";
        }


    } else if (this.classList.contains("print")) {
        block.type = "print";

        var firstDropZone = this.children[1];
        block.name = compile(firstDropZone.children[0]);

    } else if (this.classList.contains("arithmetic")) {
        block.type = "numeric_operator";

        var firstDropZone = this.children[1];
        var operator = this.children[2];
        var secondDropZone = this.children[3];

        block.arg1 = compile(firstDropZone.children[0]);
        block.arg2 = compile(secondDropZone.children[0]);

        block.name = operator.value;

    } else if (this.classList.contains("while")) {
        block.type = "while";

        var firstDropZone = this.children[1];
        var secondDropZone = this.children[3];

        block.condition = compile(firstDropZone.children[0]);

        block.commands = [];

        for (var idx = 0; idx < secondDropZone.children.length; idx++) {
            block.commands.push(compile(secondDropZone.children[idx]));
        }

    } else if (this.classList.contains("if")) {
        block.type = "if";

        var condition = this.children[1];
        var commands = this.children[3];

        block.commands = [];

        this.condition = compile(condition.children[0]);

        for (var idx = 0; idx < commands.children.length; idx++) {
            block.commands.push(compile(commands.children[idx]));
        }

    } else if (this.classList.contains("ifElse")) {

        block.type = "ifelse";

        var condition = this.children[1];
        var ifCommands = this.children[3];
        var elseCommands = this.children[5];

        this.condition = condition.children[0].compile();

        block.commands = [];
        for (var idx = 0; idx < ifCommands.children.length; idx++) {
            block.commands.push(compile(ifCommands.children[idx]));
        }

        block.else = [];
        for (var idx = 0; idx < elseCommands.children.length; idx++) {
            block.commands.push(compile(elseCommands.children[idx]));
        }

    } else if (this.classList.contains("andOr")) {
        block.type = "logic_operator";

        var firstDropZone = this.children[1];
        var operator = this.children[2];
        var secondDropZone = this.children[3];

        block.name = operator.value;
        block.arg1 = compile(firstDropZone.children[0]);
        block.arg2 = compile(secondDropZone.children[0]);

    } else if (this.classList.contains("equality")) {
        block.type = "comparison";

        var firstDropZone = this.children[1];
        var operator = this.children[2];
        var secondDropZone = this.children[3];

        block.arg1 = compile(firstDropZone.children[0]);
        block.arg2 = compile(secondDropZone.children[0]);

        block.name = operator.value;
    }

    return block;
}