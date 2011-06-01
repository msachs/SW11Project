// packager build Core/DOMReady MooTools-DatePicker/Picker.Date > mootools.js
(function(){

var preventDefault = function(event){
	event.preventDefault();
}

var attachDatePicker = function(){

	$$('a.date').addEvent('click', preventDefault);
	
	new Picker.Date($$('input.date'), {
		toggle: $$('a.date'),
		positionOffset: {x: 5, y: 0},
		pickerClass: 'datepicker_vista'
	});

}

window.addEvent('domready', attachDatePicker);

})();
