// packager build Core/DOMReady MooTools-DatePicker/Picker.Date > mootools.js
(function(){

var preventDefault = function(event){
	event.preventDefault();
}

var attachDatePicker = function(){

	var anchors = document.getElements('a.date');
	
	anchors.addEvent('click', preventDefault);
	new Picker.Date(document.getElements('input.date'), {
		toggle: anchors,
		positionOffset: {x: 5, y: 0},
		pickerClass: 'datepicker_vista'
	});

};

var listener = function(event){
	event.preventDefault();
	
	document.location = this.get('data-href').substitute({id: document.id('templateChooser').get('value')});
};

var attachTemplateChooser = function(){
	
	document.getElements('div.buttons button.download, div.buttons button.proceed').addEvent('click', listener);
	
};


window.addEvent('domready', function(){
	attachDatePicker();
	
	attachTemplateChooser();
});

})();
