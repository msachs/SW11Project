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

var activate3 = function(){
	document.getElements('div.step3, div#header').addClass('active');
	document.getElements('div.step3 > div.breadcrumb, div.step2').removeClass('active');
};

var reset3 = function(){
	document.getElements('div.step3, div#header').removeClass('active');
	document.getElements('div.step3 > div.breadcrumb, div.step2').addClass('active');
};

var attachBreadcrumbs = function(){
	
	document.getElements('.activateStep3').addEvent('click', activate3);
	document.getElements('input').addEvent('keydown', reset3);
	
};

var eraseForm = function(event){
	event.preventDefault();
	
	var form = document.id('template_send');
	if (form) form.reset();
};

var attachEraser = function(){
	
	document.getElements('a.erase').addEvent('click', eraseForm);
	
};

window.addEvent('domready', function(){
	attachDatePicker();
	
	attachTemplateChooser();
	
	attachBreadcrumbs();
	
	attachEraser();
});

})();
