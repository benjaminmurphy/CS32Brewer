<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Brewer</title>
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
            <h2 id="main1-title">main</h2>
            <h2 id="main2-title">main (cont.)</h2>
            <h2 id="main3-title">main (cont.)</h2>
            <h2 id="instructions">Instructions</h2>
            <h2 id="about" onclick="showAbout()">About</h2>
            
            
            <input type="text" id="prog_id">
            <button onclick="loadProgramFromUrl()">Go</button>
            
        </div>

        <div id="aboutDiv">
            <button class="button" type="button" id="aboutButton" onclick="showAbout()">Close</button>
            <p>Brewer &copy; 2015 Raphael Kargon, Ben Murphy, Noah Picard, Steven Shi<br>
                For more information, contact benhameeen_murfie@brown.edu.</p>
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
        </table>
    <table>
        <tr>

            <td>
                <div class="item literal bVal true boolean" draggable="true" type="bool" ondragstart="startDrag(event)" id="trueLiteral">
                    True
                </div>

            </td>
        </tr>
        <tr>
            <td>
                <div class="item literal bVal false boolean" draggable="true" type="bool" ondragstart="startDrag(event)" id="falseLiteral">
                    False
                </div>

            </td>


        </tr>

        <tr>

            <td>
                <div class="item literal sVal string" draggable="true" type="string" ondragstart="startDrag(event)" id="stringLiteral">
                    <input class="litVal litValString" name="litVal" type="string" placeholder="String">
                </div>
            </td>

        </tr>
        <tr>

            <td>
                <div class="item literal nVal number" draggable="true" type="number" ondragstart="startDrag(event)" id="numberLiteral">
                    <input class="litVal litValNumber" name="litVal" type="number" placeholder="Number">
                </div>
            </td>
        </tr>
    </table>
    <table>
        <tr>

            <td><div class="item var variable" draggable="true" ondragstart="startDrag(event)" id="var">
                <h3 class="itemHeader"></h3>
                <select class="functionSelect varSelect" name="equalityType">
                </select>
                </div></td>

        </tr>
        <tr>

            <td>
                <div class="item set void" draggable="true" ondragstart="startDrag(event)" id="set">
                    <h3 class="itemHeader">Set:</h3>
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
                <div class="item print void" draggable="true" ondragstart="startDrag(event)" id="print">
                    <h3 class="itemHeader">Print:</h3>
                    <div class="droppable single nonvoid">
                    </div>
                </div>
            </td>

        </tr>
        <tr>

            <td>
                <div class="item equality boolean" draggable="true" ondragstart="startDrag(event)" id="equality">
                    <h3 class="itemHeader"></h3>
                    <div class="droppable single">
                    </div>
                    <select class="functionSelect" name="equalityType">
                        <option value="eq">=</option>
                        <option value="less">&lt;</option>
                        <option value="greater">&gt;</option>
                    </select>
                    <div class="droppable single">
                    </div>
                </div>
            </td>

        </tr>
        <tr>

            <td>
                <div class="item andOr boolean" draggable="true" ondragstart="startDrag(event)" id="andOr">
                    <h3 class="itemHeader"> </h3>
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
                <div class="item not boolean" draggable="true" ondragstart="startDrag(event)" id="not">
                    <h3 class="itemHeader">Not</h3>
                    <div class="droppable boolean single">
                    </div>
                </div>
            </td>

        </tr>
        <tr>

            <td>
                <div class="item arithmetic number" draggable="true" ondragstart="startDrag(event)" id="arithmetic">
                    <h3 class="itemHeader"> </h3>
                    <div class="droppable single number">
                    </div>
                    <select class="functionSelect" name="andOrType">
                        <option value="add">+</option>
                        <option value="sub">-</option>
                        <option value="mul">*</option>
                        <option value="div">/</option>
                        <option value="mod">%</option>
                    </select>
                    <div class="droppable single number">
                    </div>
                </div>
            </td>

        </tr>
        <tr>

            <td>
                <div class="item if void" draggable="true" ondragstart="startDrag(event)" id="if">
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
                <div class="item ifElse void" draggable="true" ondragstart="startDrag(event)" id="ifElse">
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

            <td><div class="item while void" draggable="true" ondragstart="startDrag(event)" id="while">
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

<div id="playground1" ondrop="drop(event)" ondragover="allowDrop(event)">

</div>

<div id="playground2" ondrop="drop(event)" ondragover="allowDrop(event)">

</div>

<div id="playground3" ondrop="drop(event)" ondragover="allowDrop(event)">

</div>

<div id="console">
    <h2>console</h2>
</div>

<div id="buttonTab">
    <button id="runButton" onclick="runProgram()">Run</button>
    <button id="killButton" onclick="killProgram()">Kill</button>
    <button id="saveButton" onclick="saveProgram()">Save</button>
    <button id="consoleButton" onclick="setConsole()">Console</button>
</div>

<script src="js/interact-1.2.4.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/main.js"></script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-61467451-2', 'auto');
  ga('send', 'pageview');

</script>
</body>
</html>
