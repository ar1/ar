<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>  
<%@ page import="com.apt.Property"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
ArrayList propertyResultArray = (ArrayList)request.getAttribute("page_content");
for(int i=0; i<propertyResultArray.size(); i++) {
    Property prop = (Property)propertyResultArray.get(i);
    String propName=prop.getPROP_NAME();
    String propLong=prop.getLONG_CENTROID();
    String propLat=prop.getLAT_CENTROID();
    String propRefNum=prop.getRef_Nbr();
    System.out.println("propName");
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Apartment</title>
    <style type="text/css"> 
      html, body {
        width: 98%; height: 98%;
       }
      /*
      table {
      border-spacing: 2px;
      text-align: left;
      }
      tr {
       background-color: white;
       -webkit-transition:background-color 0.2s linear;
      }
      */
      #map-canvas {
      	margin: 0;
	    padding: 0;
        width: 100%;
        height:100%;
        float: left;
      }
      #map {
        margin: 0;
	    padding: 0;
        float: left;
      /* width: 650px; */
       height: 100%;
       width: 45%; 
      }
     #aptList {
	    padding-left:20%;
     }
     #apt-canvas {
       padding-left:20%;
  
     }
    </style> 
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDydS3wy-9NIHTzSgdCYfDsz6CTJ48e4EE&sensor=false"></script> 
<script type="text/javascript" src="/javascript/jquery/1.10.2/jquery-1.10.2.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	
	var map;
	var min = .999999;
	var max = 1.000001;
	//read locations from server
	var locations=new Array();
	<%
	for(int i=0; i<propertyResultArray.size(); i++) {
	    Property prop = (Property)propertyResultArray.get(i);
	    String propName=prop.getPROP_NAME();
	    String propLong=prop.getLONG_CENTROID();
	    String propLat=prop.getLAT_CENTROID();
	    String propRefNum=prop.getRef_Nbr();
	    System.out.println("long:"+propLong);
	    System.out.println("lat:"+propLat);
	%>
	    //push to map
		locations.push(['<%=propName%>'+"<br>Refer Number:"+'<%=propRefNum%>',<%=propLat%>,<%=propLong%>]);
	    //add list table
	    
	    $("#aptListTb").append($('<tr><td>').html("<b><%=propName%></b><br>Refer Number:<%=propRefNum%>"));
	<%}%>

	initialize= function()  {
	  var lat = (getMinCol(locations, 1) + getMaxCol(locations, 1)) / 2;
	  var longitude = (getMinCol(locations, 2) + getMaxCol(locations, 2)) / 2;
		
	  var mapOptions = {
	    zoom: 14,
	    center: new google.maps.LatLng(lat, longitude),
	    mapTypeId: google.maps.MapTypeId.ROADMAP
	  };
	  map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
	  setMarkers(map, locations);

		 var k = 0.01; 

		 var n = getMaxCol(locations, 1) + k; 
		 var e = getMaxCol(locations, 2) + k; 
		 var s = getMinCol(locations, 1) - k; 
		 var w = getMinCol(locations, 2) - k; 

		 var neNew = new google.maps.LatLng( n, e ); 
		 var swNew = new google.maps.LatLng( s, w ); 
		 var boundsNew = new google.maps.LatLngBounds( swNew, neNew ); 

		 map.fitBounds( boundsNew ); 
	};
	
	
	getMaxCol=function(array, index) {
		var max = -1000;
		for ( var i = 0; i < array.length; i++) {
			if (array[i][index] > max)
				max = array[i][index];
		}
		return max;
	};

    getMinCol=function (array, index) {
		var min = 1000;
		for ( var i = 0; i < array.length; i++) {
			if (array[i][index] < min)
				min = array[i][index];
		}
		return min;
	}; 
	

	 setMarkers= function (map, locations){
			var infowindow = new google.maps.InfoWindow();
			var addedLoc=new Array();
			for ( var i = 0; i < locations.length; i++) {
				var loc = locations[i];
				
				var finalLatLng=new google.maps.LatLng(loc[1],loc[2]);
				addedLoc.push(finalLatLng);
				//check to see if any of the existing markers match the latlng of the new marker
		        if (addedLoc.length != 0) {
		            for (var j=0; j < addedLoc.length; j++) {
		                var currentLoc = addedLoc[j];

		                //if a marker already exists in the same position as this marker
		                if (currentLoc.equals(finalLatLng)) {

		                    //update the position of the coincident marker by applying a small multipler to its coordinates
		                    var newLat = loc[1] * (Math.random() * (max - min) + min);
		                    var newLng = loc[2] * (Math.random() * (max - min) + min);

		                    finalLatLng = new google.maps.LatLng(newLat,newLng);
							break;
		                }                   
		            }
		        } 
		
				//iiii
				var image="http://maps.google.com/mapfiles/ms/icons/red-dot.png";
			//	var myLatLng = new google.maps.LatLng(loc[1], loc[2]);
				var marker = new google.maps.Marker({
					position : finalLatLng,
					map : map,
					info : loc[0],
					clickable : true,
					icon:image
				});

				google.maps.event.addListener(marker, 'click', function(e) {

				infowindow.setContent(this.info);
				infowindow.open(map, this);
				});
			} 
	 };
	 
	google.maps.event.addDomListener(window, 'load', initialize);

});
</script>
</head>
<body>
<div id="map">  
  <div id="map-canvas"></div> 
</div> 
<div id="aptList"> 
   <h2>Apt list</h2>
   <div id="apt-canvas">
   <table id="aptListTb">
   </table>
   </div>
</div> 
</body>
</html>