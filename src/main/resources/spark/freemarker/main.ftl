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

        <link rel="stylesheet" href="static/css/normalize.css">
        <link rel="stylesheet" href="static/css/main.css">
        <script src="static/js/modernizr.js"></script>
    </head>
    <body>
        <!--[if lt IE 8]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->

        <div id="header">
            <h1 id="title">Brewer</h1>
        </div>

        <div id="menu" ondrop="drop(event)" ondragover="allowDrop(event)">
            <h2>menu</h2>
            <table>

                <table>
                     <tr>

                        <td><div class="item var" draggable="true" ondragstart="startDrag(event)" id="var">
                            <h3 class="itemHeader">Var</h3>
                            <select class="functionSelect" name="equalityType">
                                    <option value="a">a</option>
                                    <option value="b">b</option>
                                    <option value="c">c</option>
                                    <option value="0">0</option>
                                    <option value="1">1</option>
                                </select>
                        </div></td>

                    </tr>
                    <tr>

                        <td>
                            <div class="item get" draggable="true" ondragstart="startDrag(event)" id="get">
                                <h3 class="itemHeader">Get</h3>
                                <div class="droppable">
                                    <h3 class="droppableHeader">Variable</h3>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>

                        <td>
                            <div class="item set" draggable="true" ondragstart="startDrag(event)" id="set">
                                <h3 class="itemHeader">Set</h3>
                                <div class="droppable">
                                    <h3 class="droppableHeader">Variable</h3>
                                </div>
                                <div class="droppable">
                                    <h3 class="droppableHeader">Value</h3>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>

                        <td>
                            <div class="item print" draggable="true" ondragstart="startDrag(event)" id="print">
                                <h3 class="itemHeader">Print</h3>
                                <div class="droppable">
                                    <h3 class="droppableHeader">Variable</h3>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>

                        <td>
                            <div class="item tallItem equality" draggable="true" ondragstart="startDrag(event)" id="equality">
                                <h3 class="itemHeader">Equality</h3>
                                <div class="droppable">
                                    <h3 class="droppableHeader">Variable</h3>
                                </div>
                                <select class="functionSelect" name="equalityType">
                                    <option value="eq">=</option>
                                    <option value="less">&lt</option>
                                    <option value="greater">&gt</option>
                                </select>
                                <div class="droppable">
                                    <h3 class="droppableHeader">Variable</h3>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>

                        <td>
                            <div class="item tallItem andOr" draggable="true" ondragstart="startDrag(event)" id="andOr">
                                <h3 class="itemHeader">And/Or</h3>
                                <div class="droppable boolean">
                                    <h3 class="droppableHeader">Boolean</h3>
                                </div>
                                <select class="functionSelect" name="andOrType">
                                    <option value="and">AND</option>
                                    <option value="or">OR</option>
                                </select>
                                <div class="droppable boolean">
                                    <h3 class="droppableHeader">Boolean</h3>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>

                        <td>
                            <div class="item tallItem arithmetic" draggable="true" ondragstart="startDrag(event)" id="arithmetic">
                                <h3 class="itemHeader">Arithmetic</h3>
                                <div class="droppable boolean">
                                    <h3 class="droppableHeader">Boolean</h3>
                                </div>
                                <select class="functionSelect" name="andOrType">
                                    <option value="add">+</option>
                                    <option value="sub">-</option>
                                    <option value="mul">*</option>
                                    <option value="div">/</option>
                                </select>
                                <div class="droppable boolean">
                                    <h3 class="droppableHeader">Boolean</h3>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>

                        <td>
                            <div class="item if" draggable="true" ondragstart="startDrag(event)" id="if">
                                <h3 class="itemHeader">If</h3>
                                <div class="droppable boolean">
                                    <h3 class="droppableHeader">Boolean</h3>
                                </div>
                                <div class="droppable">
                                    <h3 class="droppableHeader">Instructions</h3>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>

                        <td>
                            <div class="item tallItem ifElse" draggable="true" ondragstart="startDrag(event)" id="ifElse">
                                <h3 class="itemHeader">If/Else</h3>
                                <div class="droppable boolean">
                                    <h3 class="droppableHeader">Boolean</h3>
                                </div>
                                <div class="droppable">
                                    <h3 class="droppableHeader">Instructions</h3>
                                </div>
                                <div class="droppable">
                                    <h3 class="droppableHeader">Instructions</h3>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>

                        <td><div class="item while" draggable="true" ondragstart="startDrag(event)" id="while">
                            <h3 class="itemHeader">While</h3>
                            <div class="droppable boolean">
                                <h3 class="droppableHeader">Boolean</h3>
                            </div>
                            <div class="droppable">
                                <h3 class="droppableHeader">Instructions</h3>
                            </div>
                        </div></td>

                    </tr>

                </table>

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

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="static/js/interact-1.2.4.js"></script>
        <script>window.jQuery || document.write('<script src="static/js/jquery-1.11.2.min.js"><\/script>')</script>
        <script src="static/js/main.js"></script>

        <!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
        <script>
            (function(b,o,i,l,e,r){b.GoogleAnalyticsObject=l;b[l]||(b[l]=
                                                                    function(){(b[l].q=b[l].q||[]).push(arguments)});b[l].l=+new Date;
                                   e=o.createElement(i);r=o.getElementsByTagName(i)[0];
                                   e.src='//www.google-analytics.com/analytics.js';
                                   r.parentNode.insertBefore(e,r)}(window,document,'script','ga'));
            ga('create','UA-XXXXX-X','auto');ga('send','pageview');
        </script>
    </body>
</html>