/*
window.addEvent('domready', function() {
   myFormValidator = new Form.Validator.Inline($('register'), {
      stopOnFailure: true,
      useTitles: true,
      errorPrefix: "",
           
      onFormValidate: function(passed, form, event) {
         if (passed) {
            form.submit();
         }
      },

      //onElementValidate: function(passed, element, validator, is_warn) {
        // if (element.get('name') == 'agb' && !passed) {
          //  alert('Hey, the terms are really important!');
         //}
      //}
   });
});

if ($('generateFile')) {
   $('generateFile').addEvents({
      'click': function() { myFormValidator.validate(); }
   });
}

*/

function resetElement(element)
{
  element.style.borderColor="";
  element.style.backgroundColor="";
}

function myValidate()
{
  var my_elements = document.getElementsByClassName("required"); 
  var found = 0;
  for(i=0; i< my_elements.length; i++)
  {
    if(my_elements[i].type=="text" 
    || my_elements[i].type=="textarea")
    {  
      if(!my_elements[i].value)
      {
        found=1;
        my_elements[i].style.borderColor="#FF9933";
        my_elements[i].style.backgroundColor="#FFFF99";
      }
    }
  }
  if (found == 1)
  { 
    return false;
  }
}

function dots(name) {
    var dot = document.createElement("div");
    dot.setAttribute("id",name);
    dot.style.cssText = "position: relative; background-color: #000; height: 10px; width: 10px";
    document.body.appendChild(dot);
}
