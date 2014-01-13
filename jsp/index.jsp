<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Place Autocomplete Address Form</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    
    
<style type="text/css">
	  .general {
		font-family:  Verdana, Arial, Helvetica, sans-serif;
		font-weight: normal;
		font-size: 12px;
		border: 0px;
		border-collapse: collapse;
		border-spacing: 0px;
		font-color: #FFFF00;
		}

     .pac-icon{
      display:none;
      }
</style>

<script src="https://maps.googleapis.com/maps/api/js?v=3&sensor=false&libraries=places"></script>
<script type="text/javascript" src="/javascript/jquery/1.10.2/jquery-1.10.2.min.js"></script>
 <script>
// This example displays an address form, using the autocomplete feature
// of the Google Places API to help users fill in the information.



		
$(document).ready(function() {
	

	convert_state = function (name, to) {
	    var states = new Array(                         {'name':'Alabama', 'abbrev':'AL'},          {'name':'Alaska', 'abbrev':'AK'},
	        {'name':'Arizona', 'abbrev':'AZ'},          {'name':'Arkansas', 'abbrev':'AR'},         {'name':'California', 'abbrev':'CA'},
	        {'name':'Colorado', 'abbrev':'CO'},         {'name':'Connecticut', 'abbrev':'CT'},      {'name':'Delaware', 'abbrev':'DE'},
	        {'name':'Florida', 'abbrev':'FL'},          {'name':'Georgia', 'abbrev':'GA'},          {'name':'Hawaii', 'abbrev':'HI'},
	        {'name':'Idaho', 'abbrev':'ID'},            {'name':'Illinois', 'abbrev':'IL'},         {'name':'Indiana', 'abbrev':'IN'},
	        {'name':'Iowa', 'abbrev':'IA'},             {'name':'Kansas', 'abbrev':'KS'},           {'name':'Kentucky', 'abbrev':'KY'},
	        {'name':'Louisiana', 'abbrev':'LA'},        {'name':'Maine', 'abbrev':'ME'},            {'name':'Maryland', 'abbrev':'MD'},
	        {'name':'Massachusetts', 'abbrev':'MA'},    {'name':'Michigan', 'abbrev':'MI'},         {'name':'Minnesota', 'abbrev':'MN'},
	        {'name':'Mississippi', 'abbrev':'MS'},      {'name':'Missouri', 'abbrev':'MO'},         {'name':'Montana', 'abbrev':'MT'},
	        {'name':'Nebraska', 'abbrev':'NE'},         {'name':'Nevada', 'abbrev':'NV'},           {'name':'New Hampshire', 'abbrev':'NH'},
	        {'name':'New Jersey', 'abbrev':'NJ'},       {'name':'New Mexico', 'abbrev':'NM'},       {'name':'New York', 'abbrev':'NY'},
	        {'name':'North Carolina', 'abbrev':'NC'},   {'name':'North Dakota', 'abbrev':'ND'},     {'name':'Ohio', 'abbrev':'OH'},
	        {'name':'Oklahoma', 'abbrev':'OK'},         {'name':'Oregon', 'abbrev':'OR'},           {'name':'Pennsylvania', 'abbrev':'PA'},
	        {'name':'Rhode Island', 'abbrev':'RI'},     {'name':'South Carolina', 'abbrev':'SC'},   {'name':'South Dakota', 'abbrev':'SD'},
	        {'name':'Tennessee', 'abbrev':'TN'},        {'name':'Texas', 'abbrev':'TX'},            {'name':'Utah', 'abbrev':'UT'},
	        {'name':'Vermont', 'abbrev':'VT'},          {'name':'Virginia', 'abbrev':'VA'},         {'name':'Washington', 'abbrev':'WA'},
	        {'name':'West Virginia', 'abbrev':'WV'},    {'name':'Wisconsin', 'abbrev':'WI'},        {'name':'Wyoming', 'abbrev':'WY'}
	        );
	    var returnthis = false;
	    $.each(states, function(index, value){
	        if (to == 'name') {
	            if (value.abbrev.toLowerCase() == name.toLowerCase()){
	                returnthis = value.name;
	                return false;
	            }
	        } else if (to == 'abbrev') {
	            if (value.name.toLowerCase() == name.toLowerCase()){
	                returnthis = value.abbrev.toUpperCase();
	                return false;
	            }
	        }
	    });
	    return returnthis;
	};
	
	var componentForm = {
			  locality: 'long_name',                     //city
			 // sublocality: 'long_name',
			  administrative_area_level_1: 'short_name', //state
			  administrative_area_level_2: 'short_name' //country
			//  country: 'short_name',
			// postal_code: 'short_name'                  //zip
			};

     var autocomplete;
	 var selectedFlag = false;
	 // Create the autocomplete object, restricting the search
	  // to geographical location types.
	  var input = document.getElementById('searchTextField');
	  var options = {
			  types: ['(regions)'],//"locality","postal_code"'(regions)'
			  componentRestrictions: {country: 'us'}
	  };
	  autocomplete = new google.maps.places.Autocomplete(input, options);

	  // populate the address fields in the form.
	  google.maps.event.addListener(autocomplete, 'place_changed', function() {
		  selectedFlag = true;
		  params="";
		  // Get the place details from the autocomplete object.
		  var place = autocomplete.getPlace();
		  // Get each component of the address from the place details
		  // and fill the corresponding field on the form.
		  for (var i = 0; i < place.address_components.length; i++) {
		    var addressType = place.address_components[i].types[0];
		    if (componentForm[addressType]) {
		      var val = place.address_components[i][componentForm[addressType]];
		      params += "&"+addressType+"="+val;

		    }
		  }
		  params = params.substring(1,params.length);
  
	  });
	
	  $('#searchTextField').on('keydown', function() {
		    var key = event.keyCode || event.charCode;

		    if( key == 8 || key == 46 )
		    	selectedFlag = false;
		  }); 
	  
	  //send params
	  $("#addressCheckButton").click(function(){

	          
	          var predictionListLn = $(".pac-item").length;
		      // no autocompleted selected by user,use the first Prediction item
		      if (predictionListLn>0&&!selectedFlag) {
		    	  params="";

		    	   var prediction = $(".pac-container .pac-item:first").text();
		    	   var predictionV1 = $(".pac-container .pac-item .pac-item-query:first").text();
		    	   var predictionV2 = $(".pac-container .pac-item:first span:nth-child(3)").text();

		    	   //alert("flag:"+selectedFlag+"0:"+prediction+" 1:"+predictionV1+" 2:"+predictionV2);
		    	   //city&state
	 	    	   if (prediction.indexOf(",") != -1) {
	 	    		   var tmp = predictionV2.split(",");
			    	   var suggestState = tmp[tmp.length-2];
			    	   params = "locality="+predictionV1+"&administrative_area_level_1="+suggestState.substring(0,2);
		    	   } 
		    	  //state or country
		    	  else {
		    		  //state
		    		 if(predictionV2.length!=0){
		    			 var abbrevName= convert_state(predictionV1,'abbrev');
		    			 params = "administrative_area_level_1="+abbrevName;
		    			 
		    		 }
		    		    
		    		  //country
		    		 else{
		    			alert("Please input city/state/zip");
				    	return false;	 
		    		 }
				       
		  	  	  }  
		      }
		      
		      //if false, userInput
		      if(!selectedFlag&&predictionListLn==0) {
		    	  alert("Please input city/state/zip");
		    	  return false;
		      }
		   // alert(params);
		      
		    var propForm = document.createElement("FORM");   
		    
		    var url="GetPropertyProc?"+params;
		    propForm.name='propForm';
		    propForm.method="post";   
		    propForm.action=url;   
		   // propForm.target='_self';
		   	document.body.appendChild(propForm);
		    propForm.submit();
		    
		    
/* 	        var city_input = document.createElement("input");
	        city_input.type="hidden";   
	        city_input.name="city";   
	        city_input.value=locality;
	        oForm.appendChild(city_input);
	         
		    var state_input = document.createElement("input");
		    state_input.type="hidden";   
		    state_input.name="state";   
		    state_input.value=administrative_area_level_1;
		    oForm.appendChild(state_input);
		    
		    var county_input = document.createElement("input");
		    county_input.type="hidden";   
		    county_input.name="county";   
		    county_input.value=administrative_area_level_2;
	        oForm.appendChild(county_input);
	        
		    var zipcode_input = document.createElement("input");
		    zipcode_input.type="hidden";   
		    zipcode_input.name="zipcode";   
		    zipcode_input.value=postal_code;
	        oForm.appendChild(description_input); 
		    
		    document.body.appendChild(oForm); */
		   
	  });
	  

});

</script>
  </head>

  <body onload="initialize()">
   <br> <br> <br> <br>
    <div id="locationField" align="center">
     <table class="general" >
     <tr>   
     <td align="right" valign="bottom" width="200"><input id="searchTextField" placeholder="Enter your address" type="text" style="width:300px;" ></td>
     <td  align="left" valign="bottom"><input type="button" class='runTestB' value="Check" id="addressCheckButton"></td>
     </tr>
     </table>
    </div>

  </body>
</html>