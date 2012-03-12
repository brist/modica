$(document).ready(function() {
 init();
});

var sfNode = "afp\\:sf";
var sfParamNode = "afp\\:parameter";

function init() {
 $.ajax({
   type:"GET",
   url:"afp.xml",
   dataType:"xml",
   success: function(xml, txtStatus, jqXHR) {
     var modca = $("afp\\:modca", xml).children(sfNode);
     traverseXMLSFs(modca, $('#main')); 
   },
 });
}

function traverseXMLSFs(parentNode, htmlParent) {
  var htmlChild = printHtml(parentNode);
  htmlParent.append(htmlChild);
  parentNode.children(sfNode).each(function() {
    traverseXMLSFs($(this), htmlChild);
  });
}

function printHtml(afpTreeNode, htmlTreeNode) {
  
  var html = $('<ul>');
  afpTreeNode.children(sfParamNode).each(function() {
    html.append($('<li>').append('<p>').text($(this).attr('key')));
  });
  return html;
}
