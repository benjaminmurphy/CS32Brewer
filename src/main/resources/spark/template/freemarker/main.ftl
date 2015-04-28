<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <!-- Place favicon.ico in the root directory --> 

        <link rel="stylesheet" href="css/normalize.css">
        <link rel="stylesheet" href="css/main.css">
        <script src="js/modernizr.js"></script>
    </head>
    <body>
        <!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->

        <div id="header">
            <h1 id="title">Brewer</h1>
        </div>

        <div id="menu" ondrop="drop(event)" ondragover="allowDrop(event)">
            <table>

                <tr>
                    <td>
                        <h2>menu</h2>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="button makeVariable" type="button" id="makeVariable" onclick="makeVariable()">
                            Add Variable
                        </button>

                    </td>

                </tr>
                <tr>
                    <td>
                        <div class="newVarBox" id="newVarBox">
                            <input id="newVarName" name="newVarName" type="string" placeholder="Var Name"></input>
                            <select id="typeSelect" name="varType">
                                <option value="number">number</option>
                                <option value="string">string</option>
                                <option value="bool">bool</option>
                            </select>
                             <button class="button" type="button" id="addVar" onclick="addVariable()">
                                Add
                            </button>
                        </div>
                    </td>
                </tr>
                <tr>

                    <td>
                        <div class="item literal bVal true" draggable="true" type="bool" ondragstart="startDrag(event)" id="trueLiteral">
                            True
                        </div>

                    </td>

                    <td>
                        <div class="item literal bVal false" draggable="true" type="bool" ondragstart="startDrag(event)" id="falseLiteral">
                            False
                        </div>

                    </td>


                </tr>

                <tr>

                    <td>
                        <div class="item literal sVal" draggable="true" type="string" ondragstart="startDrag(event)" id="stringLiteral">
                            <input class="litVal litValString" name="litVal" type="string" placeholder="String">
                        </div>
                    </td>

                    <td>
                        <div class="item literal nVal" draggable="true" type="number" ondragstart="startDrag(event)" id="numberLiteral">
                            <input class="litVal litValNumber" name="litVal" type="number" placeholder="Number">
                        </div>
                    </td>
                </tr>
            </table>
            <table>
                <tr>

                    <td><div class="item var" draggable="true" ondragstart="startDrag(event)" id="var">
                        <h3 class="itemHeader">Var</h3>
                        <select class="functionSelect varSelect" name="equalityType">
                            <option value="a">a</option>
                        </select>
                        </div></td>

                </tr>
                <tr>

                    <td>
                        <div class="item get" draggable="true" ondragstart="startDrag(event)" id="get">
                            <h3 class="itemHeader">Get variable:</h3>
                            <div class="droppable single variable">
                            </div>
                        </div>
                    </td>

                </tr>
                <tr>

                    <td>
                        <div class="item set" draggable="true" ondragstart="startDrag(event)" id="set">
                            <h3 class="itemHeader">Set variable:</h3>
                            <div class="droppable single variable">
                            </div>
                            <h3 class="itemHeader">To:</h3>
                            <div class="droppable single">
                            </div>
                        </div>
                    </td>

                </tr>
                <tr>

                    <td>
                        <div class="item print" draggable="true" ondragstart="startDrag(event)" id="print">
                            <h3 class="itemHeader">Print variable:</h3>
                            <div class="droppable single variable">
                            </div>
                        </div>
                    </td>

                </tr>
                <tr>

                    <td>
                        <div class="item tallItem equality" draggable="true" ondragstart="startDrag(event)" id="equality">
                            <h3 class="itemHeader">Equality</h3>
                            <div class="droppable single">
                            </div>
                            <select class="functionSelect" name="equalityType">
                                <option value="eq">=</option>
                                <option value="less">&lt</option>
                                <option value="greater">&gt</option>
                            </select>
                            <div class="droppable single">
                            </div>
                        </div>
                    </td>

                </tr>
                <tr>

                    <td>
                        <div class="item tallItem andOr" draggable="true" ondragstart="startDrag(event)" id="andOr">
                            <h3 class="itemHeader">And/Or</h3>
                            <div class="droppable boolean single">
                            </div>
                            <select class="functionSelect" name="andOrType">
                                <option value="and">AND</option>
                                <option value="or">OR</option>
                            </select>
                            <div class="droppable boolean single">
                            </div>
                        </div>
                    </td>

                </tr>
                <tr>

                    <td>
                        <div class="item not" draggable="true" ondragstart="startDrag(event)" id="not">
                            <h3 class="itemHeader">Not</h3>
                            <div class="droppable boolean single">
                            </div>
                        </div>
                    </td>

                </tr>
                <tr>

                    <td>
                        <div class="item tallItem arithmetic" draggable="true" ondragstart="startDrag(event)" id="arithmetic">
                            <h3 class="itemHeader">Arithmetic</h3>
                            <div class="droppable single number">
                            </div>
                            <select class="functionSelect" name="andOrType">
                                <option value="add">+</option>
                                <option value="sub">-</option>
                                <option value="mul">*</option>
                                <option value="div">/</option>
                            </select>
                            <div class="droppable single number">
                            </div>
                        </div>
                    </td>

                </tr>
                <tr>

                    <td>
                        <div class="item if" draggable="true" ondragstart="startDrag(event)" id="if">
                            <h3 class="itemHeader">If:</h3>
                            <div class="droppable boolean single">
                            </div>
                            <h3 class="itemHeader">Then:</h3>
                            <div class="droppable">
                            </div>
                        </div>
                    </td>

                </tr>
                <tr>

                    <td>
                        <div class="item tallItem ifElse" draggable="true" ondragstart="startDrag(event)" id="ifElse">
                            <h3 class="itemHeader">If:</h3>
                            <div class="droppable boolean single">
                            </div>
                            <h3 class="itemHeader">Then:</h3>
                            <div class="droppable">
                            </div>
                            <h3 class="itemHeader">Else:</h3>
                            <div class="droppable">
                            </div>
                        </div>
                    </td>

                </tr>
                <tr>

                    <td><div class="item while" draggable="true" ondragstart="startDrag(event)" id="while">
                        <h3 class="itemHeader">While:</h3>
                        <div class="droppable boolean single">
                        </div>
                        <h3 class="itemHeader">Do:</h3>
                        <div class="droppable">
                        </div>
                        </div></td>

                </tr>

                <tr>
                    <td>
                    <div id="spacing">

                    </div>
                    </td>
                </tr>

            </table>

        </div>

        <div id="playground" ondrop="drop(event)" ondragover="allowDrop(event)">

        </div>

        <div id="console">
            <h2>console</h2>
        </div>

        <div id="buttonTab">
            <button id="runButton" onclick="runProgram()">Run</button>
            <button id="killButton" onclick="killProgram()">Kill</button>
            <button id="consoleButton" onclick="setConsole()">Console</button>
        </div>

        <script src="js/interact-1.2.4.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>
