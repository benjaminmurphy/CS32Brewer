/*
    Brewer - Front-End Code
    by Benjamin Murphy and Noah Picard
*/

//Global Definitions

//Number of block copies
var copynum = 0;

//Three columns for placing blocks
var playground1 = document.getElementById("playground1");
var playground2 = document.getElementById("playground2");
var playground3 = document.getElementById("playground3");

//Menu for choosing blocks
var menu = document.getElementById("menu");

//Console for printing program results
var consoleBox = document.getElementById("console");

//The current log line number
var lineNumber = 0;

//Whether or not a program is currently running
var programRunning = false;

//An instance for logging the server responses
var logger = null;

//Constants for resizing
var itemPadding = 10;
var minHeight = 40;
var minWidth = 100;

//List of variables used in the program
var variableList = [];

//Accepted keys in number literal entry
var allowableKeys = [8, 37, 38, 39, 40, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 189, 190];



//User Interaction

//Checks if a key is valid for number entry
function isKeyValid(event) {
    if (allowableKeys.indexOf(event.which) == -1) {
        event.preventDefault();
    }
}

//Opens the new variable box
function makeVariable() {
    if (document.getElementById("newVarBox").style.display === "block") {
        document.getElementById("newVarBox").style.display = "none"
    } else {
        document.getElementById("newVarBox").style.display = "block";
    }
}

//Displays inforation about Brewer
function showAbout() {
    if (document.getElementById("aboutDiv").style.display === "block") {
        document.getElementById("aboutDiv").style.display = "none"
    } else {
        document.getElementById("aboutDiv").style.display = "block";
    }
}

//Opens and closes the console window
function setConsole() {
    if (consoleBox.style.display == "none") {
        consoleBox.style.display = "block";
        playground.style.width = "calc(80% - 300px);";
    } else {
        consoleBox.style.display = "none";
        playground.style.width = "calc(100% - 300px);";
    }
}



//Program variable assignment

//Makes a new variable from the new variable box
function addVariableBox() {
    var box = document.getElementById("newVarBox");
    box.style.display = "none";
    var name = document.getElementById("newVarName").value;
    if (name == "") {
        return;
    }
    var type = document.getElementById("typeSelect").value;
    console.log(name);
    addVariable(name, type);

    document.getElementById("newVarName").value = "";
}

//Adds a new variable if the variable doesn't already exist
function addVariable(name, type) {
    console.log(name);
    if (isVariable(name)) {
        alert(name + " is already a variable!");
    } else {
        variableList.push({"name": name, "type": type});
        var selects = document.getElementsByClassName("varSelect");
        for(var i = 0; i < selects.length; i++) {
            var option = document.createElement('OPTION');
            option.value = name;
            option.innerHTML = name;
            selects[i].appendChild(option);
        }
    }
}

//Checks if a variable name is already taken
function isVariable(name) {
    for(var i = 0; i < variableList.length; i++) {
        if (variableList[i].name == name) {
            return true;
        }
    }
    return false;
}

//Gets the type of a variable with given name
function getType(name) {
    for(var i = 0; i < variableList.length; i++) {
        if (variableList[i].name == name) {
            return variableList[i].type;
        }
    }
    alert("Type not found.");
    return null;
}



//Dragging and dropping elements

//Allows dropping on a given event
function allowDrop(event) {
    event.preventDefault();
}

//Gets the playground that contains a given block
function playgroundThatContains(target) {
    if (playground1.contains(target)) {
        return playground1;
    } else if (playground2.contains(target)) {
        return playground2;
    } else if (playground3.contains(target)) {
        return playground3;
    } else {
        return null;
    }
}

//Handles a drop event by placing the dragged block in a parent element
function drop(event) {

    var target = event.target;
    var data = event.dataTransfer.getData("text");
    var element = document.getElementById(data);

    var parent = element.parentNode;

    if (target.classList.contains("droppable") && playgroundThatContains(target) !== null) {

        if (target.classList.contains("single")) {
            if (element.classList.contains("while") || element.classList.contains("ifElse") ||
                element.classList.contains("if")) {
                return;
            }
        }

        if (target.classList.contains("number")) {

            if (!element.classList.contains("number") &&
                !element.classList.contains("var")) {
                return;
            }

        } else if (target.classList.contains("boolean")) {

            if (!element.classList.contains("boolean") &&
                !element.classList.contains("var")) {
                return;
            }

        } else if (target.classList.contains("variable")) {

            if (target.classList.contains("lit")) {
                if (!element.classList.contains("var") && !element.classList.contains("literal")) {
                    return;
                }
            } else {

                if (!element.classList.contains("var")) {
                    return;
                }
            }

        } else if (target.classList.contains("string")) {

            if (!element.classList.contains("string") &&
                !element.classList.contains("var")) {
                return;
            }
        } else if (target.classList.contains("nonvoid")) {
            if (element.classList.contains("void")) {
                return;
            }
        }

        event.preventDefault();
        element.style.display = "block";
        target.appendChild(element);
        element.classList.add("shrinkable");
        recursiveResize(element);
    } else if (playgroundThatContains(target) !== null) {
        event.preventDefault();
        element.style.display = "block";
        playgroundThatContains(target).appendChild(element);
        element.resize();
    } else if (menu.contains(target)) {
        event.preventDefault();
        element.parentNode.removeChild(element);
    }
    if (parent && !menu.contains(parent) && parent !== null && parent.tagName === "DIV") {
        recursiveResize(parent);
    }
}

//Handles dragging by either copying an element or dragging it from its parent
function startDrag(event) {
    var original = document.getElementById(event.target.id);
    var parent = original.parentNode;
    var copy = null;

    if (playgroundThatContains(original) !== null) {
        event.dataTransfer.setData("text", original.id);
        recursiveResize(parent);
    } else if (menu.contains(original)) {
        copy = original.cloneNode(true);
        
        if (original.classList.contains("var")) {
            copy.children[1].value = original.children[1].value;
        }
        else if (original.classList.contains("arithmetic") ||
                 original.classList.contains("andOr") ||
                 original.classList.contains("equality")) {
            copy.children[2].value = original.children[2].value;
        } else if (original.classList.contains("nVal") ||
                 original.classList.contains("sVal")) {
            copy.children[0].value = original.children[0].value;
        }
        copy.id = "copy_" + copynum.toString();
        copynum += 1;
        copy.style.display = "none";
        original.appendChild(copy);
        event.dataTransfer.setData("text", copy.id);
    }
}

//Grows or shrink all parent elements.
function recursiveResize(element) {
    while (element.id !== "playground1" && element.id !== "playground2" && element.id !== "playground3" && element.id !== "menu") {
        element.resize();
        element = element.parentNode;
    }
}

//Resizes an element based on the dimensions of its children
HTMLDivElement.prototype.resize = function() {    
    var sumHeight = 0;
    var maxWidth = 0;

    if (this.classList.contains("literal") || this.classList.contains("droppable")) {
        minWidth = 100;
    } else if (this.classList.contains("item")) {
        minWidth = 120;
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

        if (child.tagName === "SELECT") {
            sumHeight += 15;   
        }

    }

    this.style.height = Math.max(sumHeight, minHeight) + 'px';
    this.style.width = Math.max(maxWidth, minWidth) + 'px';
}



//Program Execution

//Runs a program by compiling all blocks, sending the string to the server, and
//logging the results from the server to the console
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
    
    if (requestObj === null) {
        console.log("Something went wrong while compiling.");
        programRunning = false;
        return;
    }

    var request = JSON.stringify(requestObj);

    clearLogs();
    lineNumber = 0;

    $.post("/run", request, function(response) {
        response = JSON.parse(response);

        if (response.status == "failure") {

            console.log(response);

            for (var i = 0; i < response.messages.length; i++) {
                if (response.messages[i].isError) {
                    log(response.messages[i].msg, true);
                } else {
                    log(response.messages[i].msg, false);
                }            }

            programRunning = false;

        } else {
            logger = setInterval(getLogs, 50);
        }
    })
}

//Gets the value of a given literal
HTMLDivElement.prototype.getValue = function() {    
    if (this.classList.contains("nVal")) {
        var r = parseFloat(this.getElementsByTagName("input")[0].value);
        
        if (isNaN(r)) {
            alert("Please enter a valid number!");
            return null;
        }
        
        return r;
    } else if (this.classList.contains("sVal")) {
        return this.getElementsByTagName("input")[0].value;
    } else if (this.classList.contains("bVal")) {
        if (this.classList.contains("true")) {
            return true;
        } else {
            return false;
        }
    }
    return null;
}

//Clears all logs
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

//Gets the logs from the server
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

//Ends the execution of a program by sending a kill message to the server
window.onunload = killProgram;
window.onbeforeunload = killProgram;
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

//Adds a given message to the log
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



//Program Saving

//Saves the current program as a string to the server database
function saveProgram() {
    
    var req = compileMain();
    
    $.post("/save", JSON.stringify(req), function(response) {
        response = JSON.parse(response);
        
        if (response.status === "save failed") {
            console.log("Save failed.")
        } else {
            console.log("Save completed.");
            console.log(response);
            alert("Program Identifier: " + response.programUrl.match(/[0-9]+$/)[0]);
        }
    });
}

//Compiles a program into a JSON object
function compileMain() {
    
    var b = {v: true};
    
    var request = {main : []};
    for (var idx = 0; idx < playground1.children.length; idx++) {
        var elt = playground1.children[idx].compile(b);
        elt.playground = 1;
        request.main.push(elt);
    }
    for (var idx = 0; idx < playground2.children.length; idx++) {
        var elt = playground2.children[idx].compile(b);
        elt.playground = 2;
        request.main.push(elt);    
    }
    for (var idx = 0; idx < playground3.children.length; idx++) {
        var elt = playground3.children[idx].compile(b);
        elt.playground = 3;
        request.main.push(elt);
    }
    
    if (b.v) {
        return request;
    } else {
        return null;
    }
}

//Decides whether an element should be compiled
function compile(element, b) {
    if (element === null || element === undefined || element.tagName !== "DIV") {
        return null;
    } else {
        return element.compile(b);
    }
}

//Compiles an element into a JSONobject
HTMLDivElement.prototype.compile = function(b) {

    var block = {};

    if (this.classList.contains("set")) {

        block.type = "set";

        var firstDropZone = this.children[1];
        var secondDropZone = this.children[3];

        block.name = compile(firstDropZone.children[0], b);
        block.value = compile(secondDropZone.children[0], b);

    } else if (this.classList.contains("var")) {

        block.type = "var";
        var name = this.children[1].value;
        block.name = name;
        block.class = getType(name);

    } else if (this.classList.contains("literal")) {
        block.type = "literal";

        block.value = this.getValue();

        if (this.classList.contains("nVal")) {
            block.class = "number";
            
            if (block.value === null) {
                b.v = false;
            }
        } else if (this.classList.contains("sVal")) {
            block.class = "string";
        } else {
            block.class = "bool";
        }


    } else if (this.classList.contains("print")) {
        block.type = "print";

        var firstDropZone = this.children[1];
        block.name = compile(firstDropZone.children[0], b);

    } else if (this.classList.contains("arithmetic")) {
        block.type = "numeric_operator";

        var firstDropZone = this.children[1];
        var operator = this.children[2];
        var secondDropZone = this.children[3];

        block.arg1 = compile(firstDropZone.children[0], b);
        block.arg2 = compile(secondDropZone.children[0], b);

        block.name = operator.value;

    } else if (this.classList.contains("while")) {
        block.type = "while";

        var firstDropZone = this.children[1];
        var secondDropZone = this.children[3];

        block.condition = compile(firstDropZone.children[0], b);

        block.commands = [];

        for (var idx = 0; idx < secondDropZone.children.length; idx++) {
            block.commands.push(compile(secondDropZone.children[idx], b));
        }

    } else if (this.classList.contains("if")) {
        block.type = "if";

        var condition = this.children[1];
        var commands = this.children[3];

        block.commands = [];

        block.condition = condition.children[0].compile(b);

        for (var idx = 0; idx < commands.children.length; idx++) {
            block.commands.push(compile(commands.children[idx], b));
        }

    } else if (this.classList.contains("ifElse")) {

        block.type = "ifelse";

        var condition = this.children[1];
        var ifCommands = this.children[3];
        var elseCommands = this.children[5];

        block.condition = condition.children[0].compile(b);

        block.commands = [];
        block.else = [];
        for (var idx = 0; idx < ifCommands.children.length; idx++) {
            block.commands.push(compile(ifCommands.children[idx], b));
        }

        block.else = [];
        for (var idx = 0; idx < elseCommands.children.length; idx++) {
            block.else.push(compile(elseCommands.children[idx], b));
        }

    } else if (this.classList.contains("andOr")) {
        block.type = "logic_operator";

        var firstDropZone = this.children[1];
        var operator = this.children[2];
        var secondDropZone = this.children[3];

        block.name = operator.value;
        block.arg1 = compile(firstDropZone.children[0], b);
        block.arg2 = compile(secondDropZone.children[0], b);

    } else if (this.classList.contains("not")) {
        block.type = "unary_operator";

        var firstDropZone = this.children[1];

        block.name = "not";
        block.arg1 = compile(firstDropZone.children[0], b);

    } else if (this.classList.contains("equality")) {
        block.type = "comparison";

        var firstDropZone = this.children[1];
        var operator = this.children[2];
        var secondDropZone = this.children[3];

        block.arg1 = compile(firstDropZone.children[0], b);
        block.arg2 = compile(secondDropZone.children[0], b);

        block.name = operator.value;
    }

    return block;
}



//Program loading

//Loads an existing program from the server and builds the block representation
function loadProgramFromUrl() {
    
    var id = document.getElementById("prog_id").value;
    
    $.post("/getSave/"+id, function(response) {

        var response = JSON.parse(response);
        var r = JSON.parse(response.program);
        
        console.log(r);
        console.log(r["main"]);
        makeProgram(r);
    });
}

//Creates a copy of a block type in the parent element
function copyElementIntoLoc(loc, elementName){
    var original = document.getElementById(elementName);
    var copy = null;

    if (menu.contains(original)) {
        copy = original.cloneNode(true);
        copy.id = "copy_" + copynum.toString();
        copynum += 1;
        copy.style.display = "none";
        original.appendChild(copy);
    } else {
        console.log("ERROR: Found an element of ID" + elementName + "not in menu");
        return null;
    }

    var target = loc;
    var parent = copy.parentNode;

    if (target.classList.contains("droppable") && playgroundThatContains(target) !== null) {
        copy.style.display = "block";
        target.appendChild(copy);
        copy.classList.add("shrinkable");
        recursiveResize(copy);
    } else if (playgroundThatContains(target) !== null) {
        copy.style.display = "block";
        playgroundThatContains(target).appendChild(copy);
        copy.resize();
    } else if (menu.contains(target)) {
        event.preventDefault();
        copy.parentNode.removeChild(copy);
        console.log("ERROR: Put element: " + elementName + "into the menu");
        return null;
    }
    if (parent && !menu.contains(parent) && parent !== null && parent.tagName === "DIV") {
        recursiveResize(parent);
    }
    return copy;
}

//Builds the blocks of a program from a given saved string
function makeProgram(jsonObj) {
    while (playground1.firstChild) {
        playground1.removeChild(playground1.firstChild);
    }
    while (playground2.firstChild) {
        playground2.removeChild(playground2.firstChild);
    }
    while (playground3.firstChild) {
        playground3.removeChild(playground3.firstChild);
    }
    jsonObj.main.forEach(function(element) {
        if (element.playground == 1) {
            makeProgramHelp(playground1, element);
        } else if (element.playground == 2) {
            makeProgramHelp(playground2, element);
        } else if (element.playground == 3) {
            makeProgramHelp(playground3, element);
        }
    });
}

//Generates blocks based on saved the program string
function makeProgramHelp(parent, block) {
    
    if (block == null || !block.hasOwnProperty("type"))  {
        return;
    }

    var element = null;
    if (block.type == "set") {
        element = copyElementIntoLoc(parent, "set");

        var firstDropZone = element.children[1];
        var secondDropZone = element.children[3];

        makeProgramHelp(firstDropZone, block.name);
        makeProgramHelp(secondDropZone, block.value);

    } else if (block.type == "var") {
        element = copyElementIntoLoc(parent, "var");
        var name = block.name;
        if (!isVariable(name)) {
            addVariable(name, block.class);
        }
        element.children[1].value = block.name;

    } else if (block.type == "literal") {
        if (block.class == "number") {
            element = copyElementIntoLoc(parent, "numberLiteral");
            element.children[0].value = block.value;
        } else if (block.class == "string") {
            element = copyElementIntoLoc(parent, "stringLiteral");
            element.children[0].value = block.value;
        } else if (block.class == "bool") {
            if (block.value == true) {
                element = copyElementIntoLoc(parent, "trueLiteral");
            } else if (block.value == false) {
                element = copyElementIntoLoc(parent, "falseLiteral");
            } else {
                console.log("ERROR: Element value not boolean");
            }
        }

    } else if (block.type == "print") {
        element = copyElementIntoLoc(parent, "print");
        
        var firstDropZone = element.children[1];

        makeProgramHelp(firstDropZone, block.name);

    } else if (block.type == "numeric_operator") {
        element = copyElementIntoLoc(parent, "arithmetic");

        var firstDropZone = element.children[1];
        var operator = element.children[2];
        var secondDropZone = element.children[3];

        makeProgramHelp(firstDropZone, block.arg1);
        makeProgramHelp(secondDropZone, block.arg2);

        operator.value = block.name;

    } else if (block.type == "while") {
        element = copyElementIntoLoc(parent, "while");

        var conditionZone = element.children[1];
        var commandsZone = element.children[3];

        makeProgramHelp(conditionZone, block.condition);

        for (var idx = 0; idx < block.commands.length; idx++) {
            makeProgramHelp(commandsZone, block.commands[idx]);
        }

    } else if (block.type == "if") {
        element = copyElementIntoLoc(parent, "if");

        var conditionZone = element.children[1];
        var commandsZone = element.children[3];

        makeProgramHelp(conditionZone, block.condition);

        for (var idx = 0; idx < block.commands.length; idx++) {
            makeProgramHelp(commandsZone, block.commands[idx]);
        }

    } else if (block.type == "ifelse") {
        element = copyElementIntoLoc(parent, "ifElse");

        var conditionZone = element.children[1];
        var ifCommandsZone = element.children[3];
        var elseCommandsZone = element.children[5];

        makeProgramHelp(conditionZone, block.condition);

        for (var idx = 0; idx < block.commands.length; idx++) {
            makeProgramHelp(ifCommandsZone, block.commands[idx]);
        }

        for (var idx = 0; idx < block.else.length; idx++) {
            makeProgramHelp(elseCommandsZone, block.else[idx]);
        }

    } else if (block.type == "logic_operator") {
        element = copyElementIntoLoc(parent, "andOr");

        var firstDropZone = element.children[1];
        var operator = element.children[2];
        var secondDropZone = element.children[3];

        operator.value = block.name;
        makeProgramHelp(firstDropZone, block.arg1);
        makeProgramHelp(secondDropZone, block.arg2);

    } else if (block.type == "unary_operator") {
        element = copyElementIntoLoc(parent, "not");

        var firstDropZone = element.children[1];

        makeProgramHelp(firstDropZone, block.arg1);

    } else if (block.type == "comparison") {
        element = copyElementIntoLoc(parent, "equality");

        var firstDropZone = element.children[1];
        var operator = element.children[2];
        var secondDropZone = element.children[3];

        operator.value = block.name;
        makeProgramHelp(firstDropZone, block.arg1);
        makeProgramHelp(secondDropZone, block.arg2);        
    }

}
