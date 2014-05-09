/**START_Availability Page Changes-28-08-2013*/
//Function to handle form actions.
function setBusAction(actionName){ 
	//document.forms[0].action = "./busBkg.do?hiddenAction=ShowLogin";
	document.forms[0].hiddenAction.value = actionName;
	if(document.forms[0].hiddenAction.value == "PrevDayO"){
		document.forms[0].hiddenAction.value = "Search" ;
		document.forms[0].hiddenOnwardSearchDay.value = "P";
		document.forms[0].method = "POST";
		document.forms[0].action = "./busBooking_Availability";
		document.forms[0].submit();	
	}else if(actionName == "NextDayO") {
		document.forms[0].hiddenAction.value = "Search" ;
		document.forms[0].hiddenOnwardSearchDay.value = "N";
		document.forms[0].method = "POST";
		document.forms[0].action = "./busBooking_Availability";	
		document.forms[0].submit();	
	}else if(document.forms[0].hiddenAction.value == "PrevDayR"){
		document.forms[0].hiddenAction.value = "Search" ;
		document.forms[0].hiddenReturnSearchDay.value = "P";
		document.forms[0].method = "POST";
		document.forms[0].action = "./busBooking_AvailabilityR";
		document.forms[0].submit();	
	}else if(actionName == "NextDayR") {
		document.forms[0].hiddenAction.value = "Search" ;
		document.forms[0].hiddenReturnSearchDay.value = "N";
		document.forms[0].method = "POST";
		document.forms[0].action = "./busBooking_AvailabilityR";	
		document.forms[0].submit();	
	}else{
		document.forms[0].submit();
	}
}//End of 'function setBusAction(actionName){'
//Function for validating Bus search inputs
function validateBusSearch(){
	if(document.forms[0].radOnewayOrReturnTrip[2].checked){
		document.forms[0].txtMultiTripDate1.value = document.getElementById('txtdeptDateRtrip1').value;
		document.forms[0].txtMultiTripDate2.value = document.getElementById('txtdeptDateRtrip2').value;
		document.forms[0].txtMultiTripDate3.value = document.getElementById('txtdeptDateRtrip3').value;
	    //Getting FROM Place Details
	    var placeIDAndCodeAndPlaceNameFA1 = document.forms[0].hiddenFromPlaceInfo1.value ;
	    //Getting Place ID, Place Code and Name of Start Place
	    var listA1 = placeIDAndCodeAndPlaceNameFA1.split(',');
	    var placeNameFA1 = listA1[2];
	    var placeIDAndCodeAndPlaceNameTA1 = document.forms[0].hiddenToPlaceInfo1.value ;
	    var listB1 = placeIDAndCodeAndPlaceNameTA1.split(',');	
	    var placeNameTA1 = listB1[2];
	    //Getting FROM Place Details
	    var placeIDAndCodeAndPlaceNameFA2 = document.forms[0].hiddenFromPlaceInfo2.value ;
	    //Getting Place ID, Place Code and Name of Start Place
	    var listA2 = placeIDAndCodeAndPlaceNameFA2.split(',');
	    var placeNameFA2 = listA2[2];
	    var placeIDAndCodeAndPlaceNameTA2 = document.forms[0].hiddenToPlaceInfo2.value ;
	    var listB2 = placeIDAndCodeAndPlaceNameTA2.split(',');	
	    var placeNameTA2 = listB2[2];
	    //Getting FROM Place Details
	    var placeIDAndCodeAndPlaceNameFA3 = document.forms[0].hiddenFromPlaceInfo3.value ;
	    //Getting Place ID, Place Code and Name of Start Place
	    var listA3 = placeIDAndCodeAndPlaceNameFA3.split(',');
	    var placeNameFA3 = listA3[2];
	    var placeIDAndCodeAndPlaceNameTA3 = document.forms[0].hiddenToPlaceInfo3.value ;
	    var listB3 = placeIDAndCodeAndPlaceNameTA3.split(',');	
	    var placeNameTA3 = listB3[2];
	 	if(!startDateCompareForEqual(document.forms[0].hiddenCurrentDate, document.forms[0].txtMultiTripDate1)){
	 		alert("'Trip-I Date' should be greater than or equal to 'Current Date': "+document.forms[0].hiddenCurrentDate.value+"."); 
	 	}else if(!startDateCompareForEqual(document.forms[0].hiddenCurrentDate, document.forms[0].txtMultiTripDate2)){
	 		alert("'Trip-II Date' should be greater than or equal to 'Current Date': "+document.forms[0].hiddenCurrentDate.value+"."); 
	 	}else if(!startDateCompareForEqual(document.forms[0].hiddenCurrentDate, document.forms[0].txtMultiTripDate3)){
	 		alert("'Trip-III Date' should be greater than or equal to 'Current Date': "+document.forms[0].hiddenCurrentDate.value+"."); 
	 	}else if(trim(document.forms[0].txtMultiTripDate1.value) == trim(document.forms[0].txtMultiTripDate2.value)){
	  		alert("Your 'Trip-I' and 'Trip-II' journey dates are same.Select different one.");
	  	}else if(trim(document.forms[0].txtMultiTripDate1.value) == trim(document.forms[0].txtMultiTripDate3.value)){
	  		alert("Your 'Trip-I' and 'Trip-III' journey dates are same.Select different one.");
	  	}else if(trim(document.forms[0].txtMultiTripDate2.value) == trim(document.forms[0].txtMultiTripDate3.value)){
	  		alert("Your 'Trip-II' and 'Trip-III' journey dates are same.Select different one.");
	  	}else if(trim(document.forms[0].matchFromPlace1.value).length == 0){
			alert("'Trip-I From' place is required.");
			setTimeout('document.forms[0].matchFromPlace1.focus()', 10 );		
		}else if(trim(document.forms[0].matchFromPlace1.value) != trim(placeNameFA1)){
			alert("Invalid 'Trip-I From' place.");
 			setTimeout('document.forms[0].matchFromPlace1.focus()', 10 );		
 		}else if(trim(document.forms[0].matchToPlace1.value).length == 0){
 			alert("'Trip-I To' place is required.");
 			setTimeout('document.forms[0].matchToPlace1.focus()', 10 );		
 		}else if(trim(document.forms[0].matchToPlace1.value) != trim(placeNameTA1)){
 			alert("Invalid 'Trip-I To' place.");
 			setTimeout('document.forms[0].matchToPlace1.focus()', 10 );		
 		}else if(trim(document.forms[0].matchFromPlace1.value) == trim(document.forms[0].matchToPlace1.value)){
 			alert("Your 'Trip-I From' and 'Trip-I To' places are same.");
 			setTimeout('document.forms[0].matchToPlace1.focus()', 10 );		
 		}else if(trim(document.forms[0].matchFromPlace2.value).length == 0){
 			alert("'Trip-II From' place is required.");
 			setTimeout('document.forms[0].matchFromPlace2.focus()', 10 );		
 		}else if(trim(document.forms[0].matchFromPlace2.value) != trim(placeNameFA2)){
 			alert("Invalid 'Trip-II From' place.");
 			setTimeout('document.forms[0].matchFromPlace2.focus()', 10 );		
 		}else if(trim(document.forms[0].matchToPlace2.value).length == 0){
 			alert("'Trip-II To' place is required.");
 			setTimeout('document.forms[0].matchToPlace2.focus()', 10 );		
 		}else if(trim(document.forms[0].matchToPlace2.value) != trim(placeNameTA2)){
 			alert("Invalid 'Trip-II To' place.");
 			setTimeout('document.forms[0].matchToPlace2.focus()', 10 );		
 		}else if(trim(document.forms[0].matchFromPlace2.value) == trim(document.forms[0].matchToPlace2.value)){
 			alert("Your 'Trip-II From' and 'Trip-II To' places are same.");
 			setTimeout('document.forms[0].matchToPlace2.focus()', 10 );		
 		}else if(trim(document.forms[0].matchFromPlace3.value).length == 0){
 			alert("'Trip-III From' place is required.");
 			setTimeout('document.forms[0].matchFromPlace3.focus()', 10 );		
 		}else if(trim(document.forms[0].matchFromPlace3.value) != trim(placeNameFA3)){
 			alert("Invalid 'Trip-III From' place.");
 			setTimeout('document.forms[0].matchFromPlace3.focus()', 10 );		
 		}else if(trim(document.forms[0].matchToPlace3.value).length == 0){
 			alert("'Trip-III To' place is required.");
 			setTimeout('document.forms[0].matchToPlace3.focus()', 10 );		
 		}else if(trim(document.forms[0].matchToPlace3.value) != trim(placeNameTA3)){
 			alert("Invalid 'Trip-III To' place.");
 			setTimeout('document.forms[0].matchToPlace3.focus()', 10 );		
 		}else if(trim(document.forms[0].matchFromPlace3.value) == trim(document.forms[0].matchToPlace3.value)){
 			//alert("Your 'From' and 'To' places are same. We can't find you a Bus Service without knowing where you are going.");
 			alert("Your 'Trip-III From' and 'Trip-III To' places are same.");
 			setTimeout('document.forms[0].matchToPlace3.focus()', 10 );		
 		}else{
			//Getting Trip-I From Place Details
			var placeIDAndCodeAndPlaceNameF1 = document.forms[0].hiddenFromPlaceInfo1.value ;
			//Getting Place ID, Place Code and Name 
			var listF1 = placeIDAndCodeAndPlaceNameF1.split(',');	
			document.forms[0].selectFromPlace1.value = listF1[0];
			document.forms[0].hiddenFromPlaceID1.value = listF1[0];
			document.forms[0].hiddenFromPlaceCode1.value = listF1[1];
			document.forms[0].hiddenFromPlaceName1.value = listF1[2];
			//Getting Trip-I To Place Details
			var placeIDAndCodeAndPlaceNameT1 = document.forms[0].hiddenToPlaceInfo1.value ;
			//Getting Place ID, Place Code and Name 
			var listT1 = placeIDAndCodeAndPlaceNameT1.split(',');	
			document.forms[0].selectToPlace1.value = listT1[0]; 
			document.forms[0].hiddenToPlaceID1.value = listT1[0];
			document.forms[0].hiddenToPlaceCode1.value = listT1[1];
			document.forms[0].hiddenToPlaceName1.value = listT1[2]; 
			//Getting Trip-I From Place Details
			var placeIDAndCodeAndPlaceNameF2 = document.forms[0].hiddenFromPlaceInfo2.value ;
			//Getting Place ID, Place Code and Name 
			var listF2 = placeIDAndCodeAndPlaceNameF2.split(',');	
			document.forms[0].selectFromPlace2.value = listF2[0];
			document.forms[0].hiddenFromPlaceID2.value = listF2[0];
			document.forms[0].hiddenFromPlaceCode2.value = listF2[1];
			document.forms[0].hiddenFromPlaceName2.value = listF2[2];
			//Getting Trip-I To Place Details
			var placeIDAndCodeAndPlaceNameT2 = document.forms[0].hiddenToPlaceInfo2.value ;
			//Getting Place ID, Place Code and Name 
			var listT2 = placeIDAndCodeAndPlaceNameT2.split(',');	
			document.forms[0].selectToPlace2.value = listT2[0]; 
			document.forms[0].hiddenToPlaceID2.value = listT2[0];
			document.forms[0].hiddenToPlaceCode2.value = listT2[1];
			document.forms[0].hiddenToPlaceName2.value = listT2[2]; 
			//Getting Trip-I From Place Details
			var placeIDAndCodeAndPlaceNameF3 = document.forms[0].hiddenFromPlaceInfo3.value ;
			//Getting Place ID, Place Code and Name 
			var listF3 = placeIDAndCodeAndPlaceNameF3.split(',');	
			document.forms[0].selectFromPlace3.value = listF3[0];
			document.forms[0].hiddenFromPlaceID3.value = listF3[0];
			document.forms[0].hiddenFromPlaceCode3.value = listF3[1];
			document.forms[0].hiddenFromPlaceName3.value = listF3[2];
			//Getting Trip-I To Place Details
			var placeIDAndCodeAndPlaceNameT3 = document.forms[0].hiddenToPlaceInfo3.value ;
			//Getting Place ID, Place Code and Name 
			var listT3 = placeIDAndCodeAndPlaceNameT3.split(',');	
			document.forms[0].selectToPlace3.value = listT3[0]; 
			document.forms[0].hiddenToPlaceID3.value = listT3[0];
			document.forms[0].hiddenToPlaceCode3.value = listT3[1];
			document.forms[0].hiddenToPlaceName3.value = listT3[2]; 
			document.forms[0].hiddenJourneyType.value = "M";
			document.forms[0].hiddenMultiTripDate1.value =  document.forms[0].txtMultiTripDate1.value;
			document.forms[0].hiddenMultiTripTimeSlab1.value = document.forms[0].selectMultiTripTimeSlab1.options[document.forms[0].selectMultiTripTimeSlab1.selectedIndex].value;
			document.forms[0].hiddenMultiTripDate2.value =  document.forms[0].txtMultiTripDate2.value;
			document.forms[0].hiddenMultiTripTimeSlab2.value = document.forms[0].selectMultiTripTimeSlab2.options[document.forms[0].selectMultiTripTimeSlab2.selectedIndex].value;
			document.forms[0].hiddenMultiTripDate3.value =  document.forms[0].txtMultiTripDate3.value;
			document.forms[0].hiddenMultiTripTimeSlab3.value = document.forms[0].selectMultiTripTimeSlab3.options[document.forms[0].selectMultiTripTimeSlab3.selectedIndex].value;
			document.forms[0].hiddenMultiTripSearchDay1.value = "J";	
			document.forms[0].hiddenMultiTripSearchDay2.value = "J";
			document.forms[0].hiddenMultiTripSearchDay3.value = "J";
			document.forms[0].hiddenTotalPassengers.value = "1";
			document.forms[0].action = "./bsBkg_AvailabilityMT1";
			document.forms[0].submit();
			document.getElementById("tabMain").style.display = "none";
			document.getElementById("tabPreLoad").style.display = "";
  	}
  }else{  
		document.forms[0].txtOnwardDate.value = document.getElementById('txtdeptDateRtrip').value;
		if(document.forms[0].radOnewayOrReturnTrip[1].checked ){
			document.forms[0].txtReturnDate.value = document.getElementById('txtretnDateRtrip').value;
		}
		//Getting FROM Place Details
		var placeIDAndCodeAndPlaceNameFA = document.forms[0].hiddenFromPlaceInfo.value ;
		//Getting Place ID, Place Code and Name of Start Place
		var listA = placeIDAndCodeAndPlaceNameFA.split(',');
		var placeNameFA = listA[2];
		var placeIDAndCodeAndPlaceNameTA = document.forms[0].hiddenToPlaceInfo.value ;
		var listB = placeIDAndCodeAndPlaceNameTA.split(',');	
		var placeNameTA = listB[2];
		if(trim(document.forms[0].matchFromPlace.value).length == 0){
			alert("'From' place is required.");
			setTimeout('document.forms[0].matchFromPlace.focus()', 10 );		
		}else if(trim(document.forms[0].matchFromPlace.value) != trim(placeNameFA)){
			alert("Invalid 'From' place.");
			setTimeout('document.forms[0].matchFromPlace.focus()', 10 );		
		}else if(trim(document.forms[0].matchToPlace.value).length == 0){
			alert("'To' place is required.");
			setTimeout('document.forms[0].matchToPlace.focus()', 10 );		
		}else if(trim(document.forms[0].matchToPlace.value) != trim(placeNameTA)){
			alert("Invalid 'To' place.");
			setTimeout('document.forms[0].matchToPlace.focus()', 10 );		
		}else if(trim(document.forms[0].matchFromPlace.value) == trim(document.forms[0].matchToPlace.value)){
			alert("Your 'From' and 'To' places are same.");
			setTimeout('document.forms[0].matchToPlace.focus()', 10 );		
		}else if(!startDateCompareForEqual(document.forms[0].hiddenCurrentDate, document.forms[0].txtOnwardDate)){
			alert("'Onward Date' should be greater than or equal to 'Current Date': "+document.forms[0].hiddenCurrentDate.value+"."); 
		}else if(document.forms[0].radOnewayOrReturnTrip[1].checked && !startDateCompareForEqual(document.forms[0].hiddenCurrentDate, document.forms[0].txtReturnDate)){
			alert("'Return Date' should be greater than or equal to 'Current Date': "+document.forms[0].hiddenCurrentDate.value+"."); 
		}else if(document.forms[0].radOnewayOrReturnTrip[1].checked && !startDateCompare(document.forms[0].txtOnwardDate, document.forms[0].txtReturnDate)){
			alert("'Return Date' should be greater than 'Onward Date'."); 
		}else{
			//Getting FROM Place Details
			var placeIDAndCodeAndPlaceNameF = document.forms[0].hiddenFromPlaceInfo.value ;
			//Getting Place ID, Place Code and Name of From Place
			var list1 = placeIDAndCodeAndPlaceNameF.split(',');	
			document.forms[0].selectFromPlace.value = list1[0]; 
			document.forms[0].hiddenFromPlaceID.value = list1[0];
			document.forms[0].hiddenFromPlaceCode.value = list1[1];
			document.forms[0].hiddenFromPlaceName.value = list1[2];
			//Getting TO Place Details
			var placeIDAndCodeAndPlaceNameT = document.forms[0].hiddenToPlaceInfo.value ;
			//Getting Place ID, Place Code and Name of To Place
			var list2 = placeIDAndCodeAndPlaceNameT.split(',');	
			document.forms[0].selectToPlace.value = list2[0]; 
			document.forms[0].hiddenToPlaceID.value = list2[0];
			document.forms[0].hiddenToPlaceCode.value = list2[1];
			document.forms[0].hiddenToPlaceName.value = list2[2];
			document.forms[0].hiddenOnwardJourneyDate.value =  document.forms[0].txtOnwardDate.value;
			document.forms[0].hiddenOnwardTimeSlab.value = document.forms[0].selectOnwardTimeSlab.options[document.forms[0].selectOnwardTimeSlab.selectedIndex].value;
			document.forms[0].hiddenJourneyType.value = "O";	
			if(document.forms[0].radOnewayOrReturnTrip[1].checked){
				document.forms[0].hiddenReturnJourneyDate.value = document.forms[0].txtReturnDate.value;
				document.forms[0].hiddenReturnTimeSlab.value = document.forms[0].selectReturnTimeSlab.options[document.forms[0].selectReturnTimeSlab.selectedIndex].value;
				document.forms[0].hiddenJourneyType.value = "R";
			}
			document.forms[0].hiddenOnwardSearchDay.value = "J";	
			document.forms[0].hiddenReturnSearchDay.value = "J";
		    document.forms[0].hiddenTotalPassengers.value = "1";//Added only 1
			document.forms[0].method = "POST";
			document.forms[0].action = "./busBooking_Availability";
			document.forms[0].submit();
			document.getElementById("tabMain").style.display = "none";
			document.getElementById("tabPreLoad").style.display = "";
		}
 }//Main else
}//End of 'function validateBusSearch(){'	
//Function for Single Stop Search Functionality
function singleStopSearch(){
	if(document.forms[0].linkStopID1WithOtherValueO.selectedIndex == 0){
		alert("Please select 'Stopover' place for your single stop search.");
		setTimeout( 'document.forms[0].linkStopID1WithOtherValueO.focus()', 10 );
	}else{
		var linkStopID1WithOtherValueO = document.forms[0].linkStopID1WithOtherValueO.options[document.forms[0].linkStopID1WithOtherValueO.selectedIndex].value;
		var linkStopDetails = linkStopID1WithOtherValueO.split('##');
		document.forms[0].linkStopID1O.value = linkStopDetails[0];
		document.forms[0].linkStopMinTimeGapO.value = linkStopDetails[1];
		document.forms[0].linkStopMinJrnyHrsO.value = linkStopDetails[2];
		document.forms[0].linkStopName1O.value = document.forms[0].linkStopID1WithOtherValueO.options[document.forms[0].linkStopID1WithOtherValueO.selectedIndex].text;
		document.forms[0].method = "POST";
		document.forms[0].action = "./singleStopSearchO";
		document.forms[0].submit();	
	}
}//End of 'function singleStopSearch(){'

/**END_Availability Page Changes-28-08-2013*/