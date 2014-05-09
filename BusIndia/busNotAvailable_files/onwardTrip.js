/************** SEAT LAYOUT SCRIPTS ***********************/
//Function for showing selected seat images for Onward Seat Layout
function SelectSeat(sid,rcid,servID,reqFrom,maxSeats){
	//alert("SelectSeat:"+sid+":"+rcid+":"+servID+":"+reqFrom);
	var seatNoWithServID = sid.split('-');
	sid = seatNoWithServID[1];
	var seatNumbersTodisplay = document.forms[0].seatNumbersTodisplay.value ;
	//alert(":seatNumbersTodisplay:0000:"+document.forms[0].seatNumbersTodisplay.value);	
	var seatrowcols = document.forms[0].seatrowcols.value ;
	var NoOfSeats = document.forms[0]["hidNo_ofSeats"].value;
	var NoOfBerths = document.forms[0]["hidNo_ofBerths"].value;
	var ExecSeatNos = document.forms[0]["hidExecSeatNos"].value.split(",");
	var ImageSrcVal = document.getElementById(rcid).src;
	var pos = ImageSrcVal.indexOf("/images/seat");
    var imgpos = pos + 13 ;
	var flag = 0;
	if(ImageSrcVal.substring(imgpos) == "available_seat_icon.gif" || ImageSrcVal.substring(imgpos) == "ladiesSeat_icon.gif"){
		//alert("SEAT-AVAILABLE 2 SELECTED:"+ImageSrcVal.substring(imgpos));
		//document.getElementById(rcid).src= "images/seat/selected_seat_icon.gif";
		if(ImageSrcVal.substring(imgpos) == "available_seat_icon.gif"){
			//alert("testing-1");
			document.getElementById(rcid).src= "images/seat/selected_seat_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "ladiesSeat_icon.gif"){
			//alert("testing-2");
			document.getElementById(rcid).src= "images/seat/selected_seat_iconL.gif";
		}
		/**START_Bitla-WS-16-09-2013*/
		/*
		else if(ImageSrcVal.substring(imgpos) == "available_berth_icon.gif"){
			alert("testing-3");
			document.getElementById(rcid).src= "images/seat/selected_berth_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "available_berth_iconL.gif"){
			alert("testing-4");
			document.getElementById(rcid).src= "images/seat/selected_berth_iconL.gif";
		}*/
		/**END_Bitla-WS-16-09-2013*/
		//alert(document.getElementById(rcid).src);
		if(seatNumbersTodisplay==""){
			splstr = sid.substr(1);
			seatNumbersTodisplay=splstr;
			splstr1 = rcid.substr(1);
			seatrowcols=splstr1;
		}else{
			splstr = sid.substr(1);
			seatNumbersTodisplay=seatNumbersTodisplay+","+splstr;
			splstr1 = rcid.substr(1);
			seatrowcols=seatrowcols+","+splstr1;
		}
		for(var s=0;s<=ExecSeatNos.length;s++){
			if(sid.substr(1) == ExecSeatNos[s]){
				flag = 1;
				break;
			}
		}
		if(flag == 1){
			NoOfBerths = parseInt(NoOfBerths)+1;
		} else {
			NoOfSeats = parseInt(NoOfSeats)+1;
		}
	} //if(ImageSrcVal.substring(imgpos) == "selected_seat_img.gif"){
	if(ImageSrcVal.substring(imgpos) == "selected_seat_icon.gif" || ImageSrcVal.substring(imgpos) == "selected_seat_iconL.gif"){
		//alert("SEAT-SELECTED 2 AVAILABLE:"+ImageSrcVal.substring(imgpos));
		var temp = ""; var temp1 = "";
		var arrseats=document.forms[0].seatNumbersTodisplay.value.split(',');
		var arrseatrowcols=document.forms[0].seatrowcols.value.split(',');
		for(i=0;i<arrseats.length;i++) {
			selsid = sid.substr(1);
			selrcid = rcid.substr(1);
			if(arrseats[i]!=selsid){
				temp=temp+","+arrseats[i];
			}
			if(arrseatrowcols[i]!=selrcid){
				temp1=temp1+","+arrseatrowcols[i];
			}
		}
		for(var s=0;s<=ExecSeatNos.length;s++){
			if(sid.substr(1) == ExecSeatNos[s]){
				flag = 1;
				break;
			}
		}
		if(flag == 1){
			NoOfBerths = parseInt(NoOfBerths)-1;
		} else {
			NoOfSeats = parseInt(NoOfSeats)-1;
		}
		seatNumbersTodisplay=temp.substring(1);
		seatrowcols=temp1.substring(1);
		//document.getElementById(rcid).src= "images/seat/available_seat_icon.gif";
		if(ImageSrcVal.substring(imgpos) == "selected_seat_icon.gif"){
			document.getElementById(rcid).src= "images/seat/available_seat_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "selected_seat_iconL.gif"){
			document.getElementById(rcid).src= "images/seat/ladiesSeat_icon.gif";
		}
		/**START_Bitla-WS-16-09-2013*/
		/*
		else if(ImageSrcVal.substring(imgpos) == "selected_berth_icon.gif"){
			document.getElementById(rcid).src= "images/seat/available_berth_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "selected_berth_iconL.gif"){
			document.getElementById(rcid).src= "images/seat/available_berth_iconL.gif";
		}*/
		/**END_Bitla-WS-16-09-2013*/
		
	} else if(ImageSrcVal.substring(imgpos) == "available_berth_img.gif"){
		document.getElementById(rcid).src= "images/seat/selected_berth_img.gif";
		if(seatNumbersTodisplay == ""){
			splstr = sid.substr(1) ;
			seatNumbersTodisplay = splstr;
			splstr1 = rcid.substr(1) ;
			seatrowcols = splstr1;
		}else{
			splstr = sid.substr(1);
			seatNumbersTodisplay = seatNumbersTodisplay+","+splstr;
			splstr1 = rcid.substr(1) ;
			seatrowcols = seatrowcols+","+splstr1;
		} 
		NoOfBerths = parseInt(NoOfBerths)+1;
	}else if(ImageSrcVal.substring(imgpos) == "selected_berth_img.gif"){
		var temp="";
		var temp1="";
		var arrseats = document.forms[0].seatNumbersTodisplay.value.split(',');
		var arrseatrowcols = document.forms[0].seatrowcols.value.split(',');
		for(i=0;i<arrseats.length;i++) {
			selsid = sid.substr(1);
			selrcid = rcid.substr(1);
			if(arrseats[i] != selsid)	{
				temp=temp+","+arrseats[i];
			}
			if(arrseatrowcols[i] != selrcid){
				temp1=temp1+","+arrseatrowcols[i];
			}
		}
		NoOfBerths = parseInt(NoOfBerths)-1;
        seatNumbersTodisplay = temp.substring(1);
		seatrowcols = temp1.substring(1);
        document.getElementById(rcid).src = "images/seat/available_berth_img.gif";
	}
	/**START_Bitla-WS-16-09-2013*/
	else if(ImageSrcVal.substring(imgpos) == "available_berth_icon.gif" || ImageSrcVal.substring(imgpos) == "available_berth_iconL.gif"){
		//alert("BERTH-AVAILABLE 2 SELECTED:"+ImageSrcVal.substring(imgpos));
		//document.getElementById(rcid).src= "images/seat/selected_seat_icon.gif";
		if(ImageSrcVal.substring(imgpos) == "available_seat_icon.gif"){
			document.getElementById(rcid).src= "images/seat/selected_seat_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "ladiesSeat_icon.gif"){
			document.getElementById(rcid).src= "images/seat/selected_seat_iconL.gif";
		}else if(ImageSrcVal.substring(imgpos) == "available_berth_icon.gif"){
			document.getElementById(rcid).src= "images/seat/selected_berth_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "available_berth_iconL.gif"){
			document.getElementById(rcid).src= "images/seat/selected_berth_iconL.gif";
		}
		//alert(document.getElementById(rcid).src);
		if(seatNumbersTodisplay==""){
			splstr = sid.substr(1);
			seatNumbersTodisplay=splstr;
			splstr1 = rcid.substr(1);
			seatrowcols=splstr1;
		}else{
			splstr = sid.substr(1);
			seatNumbersTodisplay=seatNumbersTodisplay+","+splstr;
			splstr1 = rcid.substr(1);
			seatrowcols=seatrowcols+","+splstr1;
		}
		for(var s=0;s<=ExecSeatNos.length;s++){
			if(sid.substr(1) == ExecSeatNos[s]){
				flag = 1;
				break;
			}
		}
		if(flag == 1){
			NoOfBerths = parseInt(NoOfBerths)+1;
		} else {
			NoOfSeats = parseInt(NoOfSeats)+1;
		}
	}else if(ImageSrcVal.substring(imgpos) == "selected_berth_icon.gif" || ImageSrcVal.substring(imgpos) == "selected_berth_iconL.gif"){
		//alert("BERTH-SELECTED 2 AVAILABLE:"+ImageSrcVal.substring(imgpos));
		var temp = ""; var temp1 = "";
		var arrseats=document.forms[0].seatNumbersTodisplay.value.split(',');
		var arrseatrowcols=document.forms[0].seatrowcols.value.split(',');
		for(i=0;i<arrseats.length;i++) {
			selsid = sid.substr(1);
			selrcid = rcid.substr(1);
			if(arrseats[i]!=selsid){
				temp=temp+","+arrseats[i];
			}
			if(arrseatrowcols[i]!=selrcid){
				temp1=temp1+","+arrseatrowcols[i];
			}
		}
		for(var s=0;s<=ExecSeatNos.length;s++){
			if(sid.substr(1) == ExecSeatNos[s]){
				flag = 1;
				break;
			}
		}
		if(flag == 1){
			NoOfBerths = parseInt(NoOfBerths)-1;
		} else {
			NoOfSeats = parseInt(NoOfSeats)-1;
		}
		seatNumbersTodisplay=temp.substring(1);
		seatrowcols=temp1.substring(1);
		//document.getElementById(rcid).src= "images/seat/available_seat_icon.gif";
		if(ImageSrcVal.substring(imgpos) == "selected_seat_icon.gif"){
			document.getElementById(rcid).src= "images/seat/available_seat_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "selected_seat_iconL.gif"){
			document.getElementById(rcid).src= "images/seat/ladiesSeat_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "selected_berth_icon.gif"){
			document.getElementById(rcid).src= "images/seat/available_berth_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "selected_berth_iconL.gif"){
			document.getElementById(rcid).src= "images/seat/available_berth_iconL.gif";
		}
	}
	/**END_Bitla-WS-16-09-2013*/
	document.forms[0]["hidNo_ofSeats"].value = NoOfSeats;
	document.forms[0]["hidNo_ofBerths"].value = NoOfBerths;
	document.forms[0].seatNumbersTodisplay.value = seatNumbersTodisplay;
	document.forms[0].selhidseats.value = document.forms[0].seatNumbersTodisplay.value;
	document.forms[0].seatrowcols.value = seatrowcols;
	document.forms[0].selhidrowcols.value = document.forms[0].seatrowcols.value;
	var Returnfare = document.forms[0].Rtnfarevalue.value;
	var Len = seatNumbersTodisplay.split(",");
	var Serv_Type = document.forms[0]["hidServ_Mode"].value;
	var Serv_Tax = document.forms[0]["hidServ_tax"].value;
	var Educ_Cess = document.forms[0]["hidEduc_cess"].value;
	var result = 0;  			var Amt = 0;
	var Serv_tax_temp = 0;		var Educ_tax_temp =  0;
	var Serv_tax_temp1 = 0;		var Educ_tax_temp1 =  0;
	var totServ_Tax = 0;		var totEduc_Cess = 0;
	var totServ_Tax1 = 0;		var totEduc_Cess1 = 0;
	var BerthFareAmt = 0;
	if(document.forms[0].seatNumbersTodisplay.value == ""){
		var chklength = 0;
		result = "Rs. "+Returnfare;//0.00";// (Including all taxes)";
		document.forms[0].farevalue.value = 0.00;
	}else{
		var chklength = 1;
		var flag_fare = 0;
		var Tax_text = "";
		Amt = document.forms[0]["Basefare"].value;
		BerthFareAmt = document.forms[0]["BerthBasefare"].value;
		if(BerthFareAmt > 0){
			if(Serv_Type == "P"){
				Serv_tax_temp1 = parseFloat(Serv_Tax)*parseInt(BerthFareAmt)/100;
				Educ_tax_temp1 = parseFloat(Educ_Cess)*parseInt(BerthFareAmt)/100;
			}else{
				Serv_tax_temp1 = Serv_Tax;
				Educ_tax_temp1 = Educ_Cess;
			}
			totServ_Tax1 = parseFloat(Serv_tax_temp1)*parseInt(NoOfBerths);
			totEduc_Cess1 = parseFloat(Educ_tax_temp1)*parseInt(NoOfBerths);
		} 
		if(NoOfSeats > 0){
			if(Serv_Type == "P"){
				Serv_tax_temp = parseFloat(Serv_Tax)*parseInt(Amt)/100;
				Educ_tax_temp = parseFloat(Educ_Cess)*parseInt(Amt)/100;
			}else{
				Serv_tax_temp = Serv_Tax;
				Educ_tax_temp = Educ_Cess;
			}
			totServ_Tax = parseFloat(Serv_tax_temp)*parseInt(NoOfSeats);
			totEduc_Cess = parseFloat(Educ_tax_temp)*parseInt(NoOfSeats);
		}
		if(Serv_Tax>0){
			Tax_text = " and "+Serv_Tax+"% service tax";
		}
		var totamt=parseInt(Amt)*parseInt(NoOfSeats)+parseInt(BerthFareAmt)*parseInt(NoOfBerths)+parseFloat(totServ_Tax)+parseFloat(totEduc_Cess)+parseFloat(totServ_Tax1)+parseFloat(totEduc_Cess1); //-parseInt(totMemDiscount);
		if(Returnfare!=""){
			var fareamt = parseInt(Returnfare)+parseInt(totamt);
			var result = "Rs. "+fareamt.toFixed(2);
		} else {
			var result = "Rs. "+totamt.toFixed(2);
		}
		document.forms[0].farevalue.value = totamt;
	}
	onwardImageIDs = rcid+",";
	var oSeats = document.forms[0].seatNumbersTodisplay.value.split(',');
	totalSeatsTodisplay =  maxSeats  ;
	if(reqFrom == "AUTO"){
		document.getElementById("seatNoLbl").innerHTML ="Allocated Seat No(s).:";
		var tempO1 = "";
		var oSeatsFinal = new Array(); 
		oSeatsFinal = removeDuplicateElement(oSeats);
		oSeats = oSeatsFinal;
		for(i=0;i<oSeats.length;i++) {
			tempO1 = tempO1+","+oSeats[i];
		}
		document.forms[0].seatNumbersTodisplay.value = tempO1.substring(1);
	}
	if(oSeats.length > totalSeatsTodisplay && reqFrom == "SL"){
		document.getElementById("seatNoLbl").innerHTML ="Selected Seat No(s).:";
		var tempO = "";
		changeID = servID+"-O"+oSeats[0];
		ImageSrcVal = document.getElementById(changeID).src;
		if(ImageSrcVal.substring(imgpos) == "selected_seat_icon.gif"){
			document.getElementById(changeID).src = "images/seat/available_seat_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "selected_seat_iconL.gif"){
			document.getElementById(changeID).src = "images/seat/ladiesSeat_icon.gif";
		}
		/**START_Bitla-WS-16-09-2013*/
		else if(ImageSrcVal.substring(imgpos) == "selected_berth_icon.gif"){
			document.getElementById(changeID).src = "images/seat/available_berth_icon.gif";
		}else if(ImageSrcVal.substring(imgpos) == "selected_berth_iconL.gif"){
			document.getElementById(changeID).src = "images/seat/available_berth_iconL.gif";
		}
		/**END_Bitla-WS-16-09-2013*/
		for(i=1;i<oSeats.length;i++) {
			tempO = tempO+","+oSeats[i];
		}
		document.forms[0].seatNumbersTodisplay.value = tempO.substring(1);
		oSeats = document.forms[0].seatNumbersTodisplay.value.split(',');
		if(oSeats.length > totalSeatsTodisplay){
			tempO = document.forms[0].seatNumbersTodisplay.value;
			document.forms[0].seatNumbersTodisplay.value = tempO.substring(2);
			alert(document.forms[0].seatNumbersTodisplay.value);
		}
	}
	/*Hiding all division IDs starting with 'OT' */
	var div = document.getElementsByTagName('div');
	var seatDvCnt = 0;
	for(var i=0; i<div.length; i++){ 
		if(div[i].id.substring(0,10) == "dvOSeatNos"){
			seatDvCnt++;
		}
	}
	var dvOSeatNos = 'dvOSeatNos'+servID;
	var dvOSeatNosA = 'dvOSeatNosA'+servID;
	//alert("seatNumbersTodisplay:"+document.forms[0].seatNumbersTodisplay.value+" :dvOSeatNos: "+dvOSeatNos);
	document.getElementById(dvOSeatNos).style.display = "";
	document.getElementById(dvOSeatNos).innerHTML = document.forms[0].seatNumbersTodisplay.value;
	document.getElementById(dvOSeatNosA).innerHTML = document.forms[0].seatNumbersTodisplay.value;
	document.forms[0].hiddenOAutoSelectedSeatNos.value = document.forms[0].seatNumbersTodisplay.value;
	document.forms[0].hiddenOSeatAutoSelectedStatus.value = "N";
}//End of 'function SelectSeat(sid,rcid,servID,reqFrom){'
