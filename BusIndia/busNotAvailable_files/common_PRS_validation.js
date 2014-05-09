	function isAlpha(field)
	{

		field1=trim(field)
		
		var valid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
		var ok = "yes";
		var temp;
		// Iterates through the field length to check each character is alphanueric or not	
		for (var i=0; i<field.value.length; i++)
		{
			temp = "" + field.value.substring(i, i+1);
			// If any character is not alphanumeric then assigns "no" to ok			
			if (valid.indexOf(temp) == "-1") ok = "no";
		}
		// If ok is "yes" then returns true	
		if (ok == "yes")
		{
			return true
		}
		// Otherwise returns false		
		return false
	}
    //
	function trim(inputString)
	{
		// Removes leading and trailing spaces from the passed string. Also
		// removes consecutive spaces and replaces it with one space.
		var retValue = inputString;
	
		if(retValue!="")
		{
			retValue = String(retValue);
			var ch = retValue.substring(0, 1);
			while (ch == " ")
			{
				// Check for spaces at the beginning of the string
				retValue = retValue.substring(1, retValue.length);
				ch = retValue.substring(0, 1);
			}
	
			ch = retValue.substring(retValue.length-1, retValue.length);
			while (ch == " ")
			{
				// Check for spaces at the end of the string
				retValue = retValue.substring(0, retValue.length-1);
				ch = retValue.substring(retValue.length-1, retValue.length);
			}
	
			while (retValue.indexOf("  ") != -1)
			{
				// Note that there are two spaces in the string - look for multiple spaces within the string
				retValue = retValue.substring(0, retValue.indexOf("  ")) + retValue.substring(retValue.indexOf("  ")+1, retValue.length); // Again, there are two spaces in each of the strings
			}
		}
		return retValue; 
	} // Ends the "trim" function

	// Checks whether the given string is numeric or not
	function isNumeric(field)
	{
		field1=trim(field)
		
		var valid = "0123456789"
		var ok = "yes";
		var temp;
		// Iterates through the field to check each character is numeric or not
		for (var i=0; i<field.value.length; i++)
		{
			temp = "" + field.value.substring(i, i+1);
			// If any character is non-numeric assigns "no" to ok
			if (valid.indexOf(temp) == "-1") ok = "no";
		}
	
		// If ok equals "yes" returns true
		// Otherwise returns false
		if (ok == "yes")
		{
			return true
		}
		return false
	}



	// Checks whether the given string is numeric or not
	function isValidPhone(field)
	{
		field1=trim(field)
		
		var valid = "0123456789,-/;"
		var ok = "yes";
		var temp;
		// Iterates through the field to check each character is numeric or not
		for (var i=0; i<field.value.length; i++)
		{
			temp = "" + field.value.substring(i, i+1);
			// If any character is non-numeric assigns "no" to ok
			if (valid.indexOf(temp) == "-1") ok = "no";
		}
	
		// If ok equals "yes" returns true
		// Otherwise returns false
		if (ok == "yes")
		{
			return true
		}
		return false
	}	

	function isValidDecimal(argvalue) 
	{
                
		//var comma=',';
		var period='.';
		
		var checkOK = "0123456789"+period  ;
		var checkStr = argvalue;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		//alert("length : "+	argvalue.value.length);
		for (i = 0;  i < argvalue.value.length;  i++)
			{
				ch = checkStr.value.charAt(i);
				for (j = 0;  j < checkOK.length;  j++)
					if (ch == checkOK.charAt(j))
					break;
				if (j == checkOK.length)
				{
					allValid = false;
					break;
				}
				if (ch != ".")
					allNum += ch;
			}
			if (!allValid)
			{	
		
				return (false);
			}

	}
	// Checks whether the given string is numeric or not
function isDecimal(field)
{
	field1=trim(field)
	var count=0;
	var valid = "0123456789."
	var ok = "yes";
	var temp;
	// Iterates through the field to check each character is numeric or not
	for (var i=0; i<field.value.length; i++)
	{
		temp = "" + field.value.substring(i, i+1);
		// If any character is non-numeric assigns "no" to ok
		if (valid.indexOf(temp) == "-1") ok = "no";
	}
	
	for (var i=0; i<field.value.length; i++)
	{
			
		if(field.value.charAt(i)== ".") count=count+1;
	}
	if(count >=2)ok = "no";
	// Otherwise returns false
	if (ok == "yes")
	{
		return true
	}
	return false
}


		// Checks whether the given string is numeric or not
	function isDecimalFor42(field, name) 
	{
		
		field1=trim(field)
		field1 = field.value;
		field2 = trim(field)
		var isValidField = "true";
		var valid = "0123456789."
		var ok = "yes";
		var temp;
		// Iterates through the field to check each character is numeric or not
		for (var i=0; i<field.value.length; i++)
		{
			temp = "" + field.value.substring(i, i+1);
			// If any character is non-numeric assigns "no" to ok
			if (valid.indexOf(temp) == "-1") ok = "no";

		}

		// If ok equals "yes" returns true
		// Otherwise returns false
		if (ok == "yes")
		{
			var hr=field.value;
			var st = new String(hr);
			var i = st.indexOf(".");
			if (i == field.value.length - 1) {
				alert("Invalid "+name+" :Should be of 00.00 format");
				field.focus();
				return false;
			}
			if (i > 2) {
				alert("Invalid "+name+" :Should be of 00.00 format");
				field.focus();
				return false;
			}
		    var mm=st.substring(i+1,hr.length);
		    mm = trim(mm);
		    if(mm.length == 1){
				mm = "0" + mm;
			}
		   	var m=parseInt(mm);
			var hh='';
			if (i==2){
		    	hh=st.substring(0,2);	
		    }
		    hh = trim(hh);
			if (i==1){ 
				hh=st.substring(0,1);
		       	hh = "0" + hh;
		    }	   
		        
		    var h=parseInt(hh);
		 
			field=hh+'.'+mm;
			
			field1 = field;
			if ((field1.indexOf(".") == (field1.length-1)) && (field1 != "")) {
				alert("Invalid "+name+" :Should be of 00.00 format");
				isValidField = "false";
				field.focus();
				return false
			}
			if (field1.indexOf(".") == -1 && field.length > 2) { 
				alert("Invalid "+name+" :Should be of 00.00 format");
				isValidField = "false";
				field.focus();
				return false			
			}
			if (field1.indexOf(".") == 0 && field.length > 3) { 
				alert("Invalid "+name+" :Should be of 00.00 format");
				isValidField = "false" ;
//				field.focus();
				return false			
			}
			// Checks if session rate is a whole number and within the given range			
			if (field1.indexOf(".") == -1 && isValidField == "true") {
				// If field is not empty then adds ".0" to field		
				if (field != "") { 
					field.value += ".00";
				}
				// Otherwise sets the focus to field field				
				else {
					field.focus();
				}
				return true
			}

			if (field1.indexOf(".") == 0 && isValidField == "true") {
				// If field is not empty then adds "0." to field		
				if (field != "") {
					field.value = "00" + field.value;
				}
				// Otherwise sets the focus to session rate field				
				else {
					field.focus();
				}
				return true
			
			}
			if(field1 <= "99.99"){
				return true
			}
		}else{
			alert("Invalid "+name+" :Should be of 00.00 format");
			field.focus();
			return false
		}
	}

		// Checks whether the given string is numeric or not
	function isDecimalFor62(field, name) 
	{
		
		field1=trim(field)
		field1 = field.value;
		field2 = trim(field)
		var isValidField = "true";
		var valid = "0123456789."
		var ok = "yes";
		var temp;
		// Iterates through the field to check each character is numeric or not
		for (var i=0; i<field.value.length; i++)
		{
			temp = "" + field.value.substring(i, i+1);
			// If any character is non-numeric assigns "no" to ok
			if (valid.indexOf(temp) == "-1") ok = "no";

		}

		// If ok equals "yes" returns true
		// Otherwise returns false
		if (ok == "yes")
		{
			var hr=field.value;
			var st = new String(hr);
			var i = st.indexOf(".");
			if (i == field.value.length - 1) {
				alert("Invalid "+name+" :Should be of 0000.00 format");
				field.focus();
				return false;
			}
			if (i > 4) {
				alert("Invalid "+name+" :Should be of 0000.00 format");
				field.focus();
				return false;
			}
		    var mm=st.substring(i+1,hr.length);
		    mm = trim(mm);
		    if(mm.length == 1){
				mm = "0" + mm;
			}
		   	var m=parseInt(mm);
			var hh='';
			if (i==4){
		    	hh=st.substring(0,4);	
		    }
		    hh = trim(hh);
			if (i==1){ 
				hh=st.substring(0,1);
		       	hh = "000" + hh;
		    }	   
		    if (i==2){ 
				hh=st.substring(0,2);
		       	hh = "00" + hh;
		    }	   
		    if (i==3){ 
				hh=st.substring(0,3);
		       	hh = "0" + hh;
		    }	       
		    var h=parseInt(hh);
		 
			field=hh+'.'+mm;
			
			field1 = field;
			if ((field1.indexOf(".") == (field1.length-1)) && (field1 != "")) {
				alert("Invalid "+name+" :Should be of 0000.00 format");
				isValidField = "false";
				field.focus();
				return false
			}
			if (field1.indexOf(".") == -1 && field.length > 4) { 
				alert("Invalid "+name+" :Should be of 0000.00 format");
				isValidField = "false";
				field.focus();
				return false			
			}
			if (field1.indexOf(".") == 0 && field.length > 5) { 
				alert("Invalid "+name+" :Should be of 0000.00 format");
				isValidField = "false" ;
//				field.focus();
				return false			
			}
			// Checks if session rate is a whole number and within the given range			
			if (field1.indexOf(".") == -1 && isValidField == "true") {
				// If field is not empty then adds ".0" to field		
				if (field != "") { 
					field.value += ".00";
				}
				// Otherwise sets the focus to field field				
				else {
					field.focus();
				}
				return true
			}

			if (field1.indexOf(".") == 0 && isValidField == "true") {
				// If field is not empty then adds "0." to field		
				if (field != "") {
					field.value = "00" + field.value;
				}
				// Otherwise sets the focus to session rate field				
				else {
					field.focus();
				}
				return true
			
			}
			if(field1 <= "9999.99"){
				return true
			}
		}else{
			alert("Invalid "+name+" :Should be of 0000.00 format");
			field.focus();
			return false
		}
	}
	// This function validates levy amount value
	function checkLevyAmount(field) {
		var amount = field.value;
		var isValidAmount = "true";
		var fieldPaymentAmount = trim(field.value);
		// Checks if levy amount length is not equals zero		
		if(fieldPaymentAmount.length != 0){
			// Checks if levy amount is not a number then send an alert message and sets focus to this field				
			if (isNaN(amount)) {
				alert("levy Amount should be numeric");
				isValidAmount = "false";
				return isValidAmount;
			}
			// Checks if levy amount is less than 0 and greater than equals 10000			
			if (parseFloat(amount) < 0 || parseFloat(amount) >= 10000) {
				alert("levy Amount should be between 0 to 9999.99");
				isValidAmount = "false";
				return isValidAmount;
			}
			// Checks if levy amount is not a valid float value
			// then sends an alert message and sets focus to this field			
			if ((amount.indexOf(".") == 0 || amount.indexOf(".") == (amount.length-1)) && (amount != "")) {
				alert("Invalid levy Amount");
				isValidAmount = "false";
				return isValidAmount;
			}
			// Checks if levy amount is a whole number and within the given range			
			if (amount.indexOf(".") == -1 && isValidAmount == "true") {
				if (amount != "") {
					field.value += ".00";
					
				}else {
					field.focus();
				}
				return isValidAmount;	
			}
			return isValidAmount;	
		}
	}

		// Function to validate and format time value as HH:MM AM/PM
	function validateTime(timeField) {
		var time = trim(timeField.value);
		var timeLength = time.length;
		var hrs = "";
		var mns = "";
		var clnIndex = "";
		var space = "";
		var isProperFormat = 0;
		// If time length is not equals 0 then gets colon index and space index	
		if (timeLength != 0) {
			clnIndex = time.indexOf(":");
			space = time.indexOf(" ");
			// If time length is greater than 5 and space index is not -1	
			if (timeLength > 5 && space != -1) {
				isProperFormat = 1;
				// Gets am/pm value from the given time				
				var ampm = time.substring(space+1, space+3);
				// Sets time value without am/pm				
				time = time.substring(0,space);
				timeLength = time.length;
				// Checks if ampm value is not valid then 
				// sends an alert message 
				// sets focus to time field 
				// and returns false	
				if ((ampm != "AM" && ampm != "PM") && (ampm != "am" && ampm != "pm")) {
					alert("Please enter a valid time format");
					timeField.focus();
					return false;
				}
			}else if (timeLength > 5 && space == -1){
					alert("Please enter a valid time");
					timeField.focus();
					return false;				
			}
		}

		// Checks if space index equals -1 and time length is greater than 0
		if (space == -1 && timeLength > 0) {
			if (clnIndex != -1) {
				hrs = time.substring(0, clnIndex);
				mns = time.substring(clnIndex+1, timeLength);
			}else {
				hrs = time.substring(0, 2);
				mns = time.substring(2, 4);			
			}
			// Checks hours and minutes value for nonnumeric then 
			// sets the focus to time field and returns false
			if (!checkNumericHrsMns(hrs, mns)) {
				timeField.focus();
				return false;
			}
		}
		
		// switch to particular case depending on the timeLength
		switch (timeLength) {
 			// If time length is 1		
			case 1: // Adds "0" to time value and sets to hours
					// Sets minutes as "00"					
					hrs = "0" + trim(time);
					mns = "00";
					// Checks if hours and minutes values are numeric 
					// then formats the time value to standard format
					if (checkNumericHrsMns(hrs, mns)) {
						properTime(hrs, mns, timeField, isProperFormat, ampm);
					}
					// Otherwise sets focus to time field and returns false					
					else {
						timeField.focus();
						return false;
					}
					break;

 			// If time length is 2	
			case 2: // Sets time value to hours
					// Sets "00" to minutes			
					hrs = trim(time.substring(0,2));
					mns = "00";
					// Checks if hours and minutes values are numeric 
					// then formats the time value to standard format					
					if (checkNumericHrsMns(hrs, mns)) {
						properTime(hrs, mns, timeField, isProperFormat, ampm);
					}
					// Otherwise sets focus to time field and returns false						
					else {
						timeField.focus();
						return false;
					}
					break;

 			// If time length is 3	
			case 3: // Checks if colon index is -1
					if (clnIndex != -1) {
						// Sets a pattern for 2 digits					
						var patt = /\d{2}/;
						// Gets hours and minutes value from time						
						hrs = trim(time.substring(0, clnIndex));
						mns = trim(time.substring(clnIndex+1, clnIndex+2));
						// Checks if hours and minutes values are numeric	
						if (checkNumericHrsMns(hrs, mns)) {
							// Checks if hours value is not 2 digits then adds "0" before hours value						
							if (!patt.test(hrs)) {
								hrs = "0" + hrs;
							}
							// Checks if minutes value is not 2 digits then adds "0" before minutes value		
							if (!patt.test(mns)) {
								mns = "0" + mns;
							}
						}
						// Checks if hours and minutes values are not numeric then sets focus to time field
						// and returns false						
						else {
							timeField.focus();
							return false;
						}
					}
					// If time value does not contain colon					
					else {
						// Sets a pattern for 2 digits					
						var patt = /\d{2}/;
						// Gets hours and minutes value from time						
						hrs = trim(time.substring(0, 1));
						mns = trim(time.substring(1, 3));
						// Checks if hours and minutes values are numeric	
						if (checkNumericHrsMns(hrs, mns)) {
							// Checks if hours value is not 2 digits then adds "0" before hours value												
							if (!patt.test(hrs)) {
								hrs = "0" + hrs;
							}
							// Checks if minutes value is not 2 digits then adds "0" before minutes value	
							if (!patt.test(mns)) {
								mns = "0" + mns;
							}
						}
						// Checks if hours and minutes values are not numeric then sets focus to time field
						// and returns false						
						else {
							timeField.focus();
							return false;
						}
					}
					// Calls properTime function to format the time with standard format	
					properTime(hrs, mns, timeField, isProperFormat, ampm);
					break;
			// If time length is 4					
			case 4: // Checks if colon index is -1
					if (clnIndex != -1) {
						// Sets a pattern for 2 digits					
						var patt = /\d{2}/;
						// Gets hours and minutes value from time						
						hrs = trim(time.substring(0, clnIndex));
						mns = trim(time.substring(clnIndex+1, clnIndex+3));
						// Checks if hours and minutes values are numeric	
						if (checkNumericHrsMns(hrs, mns)) {
							// Checks if hours value is not 2 digits then adds "0" before hours value						
							if (!patt.test(hrs)) {
								hrs = "0" + hrs;
							}
							// Checks if minutes value is not 2 digits then adds "0" before minutes value		
							if (!patt.test(mns)) {
								mns = "0" + mns;
							}
						}
						// Checks if hours and minutes values are not numeric then sets focus to time field
						// and returns false						
						else {
							timeField.focus();
							return false;
						}
					}
					// If time value does not contain colon					
					else {
						// Gets hours and minutes value from time					
						hrs = trim(time.substring(0, 2));
						mns = trim(time.substring(2, 4));
						// Checks if hours and minutes values are not numeric then sets focus to time field
						// and returns false	
						if (!checkNumericHrsMns(hrs, mns)) {
							timeField.focus();
							return false;
						}
					}
					// Calls properTime function to format the time with standard format	
					properTime(hrs, mns, timeField, isProperFormat, ampm);
					break;
 			// If time length is 5	
			case 5: // Checks if colon index is -1
					if (clnIndex != -1) {
						// Gets hours and minutes value from time					
						hrs = trim(time.substring(0, clnIndex));
						mns = trim(time.substring(clnIndex+1, clnIndex+3));
						// Checks if hours and minutes values are numeric 
						// then formats the time value to standard format	
						if (checkNumericHrsMns(hrs, mns)) {
							properTime(hrs, mns, timeField, isProperFormat, ampm);
						}
						// Checks if hours and minutes values are not numeric then sets focus to time field
						// and returns false						
						else {
							timeField.focus();
							return false;
						}
					}
					// If time value does not contain colon then sends an alert message, sets focus to time field
					// and returns true					
					else {
						alert("Please enter a valid time format");
						timeField.focus();
						return false;
					}
	
					break;
		}
		
	
	}

	// Function checks numeric value for hours and minutes
	function checkNumericHrsMns(hrs, mns) {
		// Checks if hours value is not a number then sends an alert message and returns false	
		if (isNaN(hrs)) {
			alert ("Invalid time. Please enter number.");
			return false;
		}
		// Checks if minutes value is not a number then sends an alert message and returns false		
		else if (isNaN(mns)) {
			alert ("Invalid time. Please enter number.");
			return false;
		}
		// Otherwise returns true	
		return true;
	}

	// Function converts hours and minutes value to proper format
	function properTime(hrs, mins, timeField, isProperFormat, ampm) {
		// Checks if minutes value is greater than 60 or hours value is greater than 12 and hours equals "00"
		// then alerts a message, sets focus to time field and returns false	
		if ((mins >= 60) || (hrs > 23.59)) {
				alert("Please enter a valid time");
				timeField.focus();
				return false;			
		} 
		else if(hrs == '00'){
			
			 if(mins < 0){
			 alert("Mins: "+mins);
				alert("Please enter a valid time:Time Should Not be < 00:00");
				timeField.focus();
				return false;	
			}
			else{
				timeField.value = hrs + ":" + mins ;
			}
		}
		else {
		
				timeField.value = hrs + ":" + mins ;
			// Checks if hours value is 12 or hours value is greater than 0 and less than 7
			/*if ((hrs == '12') || (hrs > '00' && hrs < '07')) {
				// Checks if user entered am/pm value
				// formats the time value with the given am/pm
				if (isProperFormat == 1) {
					timeField.value = hrs + ":" + mins + " " + ampm;
				}
				// Otherwise sets the time value with "PM"				
				else  {
					timeField.value = hrs + ":" + mins + " PM";
				}
			}*/
			// Checks if hours value is greater than 7 and less than 12			
			/*if (hrs >= '07' && hrs < '12') {
				// Checks if user entered am/pm value
				// formats the time value with the given am/pm				
				if (isProperFormat == 1) {
					timeField.value = hrs +":" + mins + " " + ampm;
				}
				// Otherwise sets the time value with "AM"					
				else {
					timeField.value = hrs + ":" + mins + " AM";
				}
			}*/
		}
		
	}
	// Function to validate Operations Days of a Service
	function validateOperationsDay(field)
	 {
	 	//field=field.toUpperCase();
	 	var checkOK = "YN" ;
		var checkStr = field.value;
		var allValid = true;
			if(checkStr.length = 7)
			{
				for (i = 0;  i < 7;  i++)
				{
					ch = checkStr.charAt(i);
					for (j = 0;  j < checkOK.length;  j++)
						if (ch == checkOK.charAt(j))
						break;
					if (j == checkOK.length)
					{
						allValid = false;
						break;
					}
				
				 }
			}
			else 
			{
				allValid = false;
			}
		/*	else
			{
				allValid = false;
			}
			*/
		/*	if (!allValid)
			{	
				//field.focus();
				alert("Invalid data: should be in YN format");
			}
		*/	
		return allValid;
		}
	
	
		// This function compares the given two dates ( b > a)
	function startDateCompare(a, b) 
	{
		b.value = trim(b.value);
		// Checks if second date length is more than zero		
		if (b.value.length > 0) 
		{
			var fromDate = new Date(convertDateFormat(a.value));		
			var toDate = new Date(convertDateFormat(b.value));	
			// Gets time difference of both the dates				
			diff = toDate.getTime() - fromDate.getTime();
		    diff = Math.floor(diff / (1000 * 60 * 60 * 24) );
			// Checks if difference is more than zero then returns true		    
			if (diff > 0) 
			{
				return true;
			} 
			// Otherwise returns false and sets focus to second date field			
			else 
			{
				
				return false;
			}
		 }
		 // If second date length is zero then returns true		 
		 else 
		 {
		 	return true;
		 }
	}
	
		//// This function compares the given two dates ( b >= a)
		function startDateCompareForEqual(a, b) 
		{
		b.value = trim(b.value);
		// Checks if second date length is more than zero		
		if (b.value.length > 0) 
		{
			var fromDate = new Date(convertDateFormat(a.value));		
			var toDate = new Date(convertDateFormat(b.value));	
			// Gets time difference of both the dates				
			diff = toDate.getTime() - fromDate.getTime();
		    diff = Math.floor(diff / (1000 * 60 * 60 * 24) );
			// Checks if difference is more than zero then returns true		    
			if (diff >= 0) 
			{
				return true;
			} 
			// Otherwise returns false and sets focus to second date field			
			else 
			{
				//b.focus();
				return false;
			}
		 }
		 // If second date length is zero then returns true		 
		 else 
		 {
		 	return true;
		 }
	}
	
	//This function compares the given two dates (-2:invalid , -1: b < a , 0:  b = a, 1: b > a  )
	function compareDate(a, b){
		//b = trim(b);
		//Checks if second date length is more than zero		
		if (a.length > 0 && b.length > 0 ) {
			var fromDate = new Date(convertDateFormat(a));		
			var toDate = new Date(convertDateFormat(b));	
			//Gets time difference of both the dates				
			diff = toDate.getTime() - fromDate.getTime();
		    diff = Math.floor(diff / (1000 * 60 * 60 * 24) );
		    //0:  b = a
		    if(diff == 0) {
				//alert(" b = a ");
				return 0;
			}
			//1: b > a
			else if(diff > 0) {
				//alert(" b > a ");
				return 1;
			}
			//-1: b < a
			else if(diff < 0) {
				//alert(" b < a ");
				return -1;
			} 
			//-2:invalid
			else{
				return -2;
			}
		 }
		 //-2:invalid	 
		 else{
		 	return -2;
		 }
	}//End of 'function DateCompareForEqual(a, b){'
	//function to convert the given dd/mm/yyyy date format to mm/dd/yyyy date format
	function convertDateFormat(ddmmyy){
		var dd = ddmmyy.substring(0,2);
		var mm = ddmmyy.substring(3,5);
		var yy = ddmmyy.substring(6,10);
		
		var mmddyy = mm + '/' + dd + '/' + yy;
		return mmddyy;
	}
	
	//Function to Check whether the given date is less than the Current Date
	function currentDateCompare(a)	{
		//Getting Current Date 
		var d = new Date();
		//Getting Current Day
		var curr_day = d.getDay();
		var curr_date = d.getDate();
		//Getting Current Month
		var curr_month = d.getMonth()+1;
		//Getting Current Year
		var curr_year = d.getFullYear();
		

		flag=true;

		a.value = trim(a.value);
		toDate=trim(a.value);
		
		y1=curr_year;
		m1=curr_month;
		d1=curr_date;
		
		y2=toDate.substring(6,10);
		m2=toDate.substring(3,5);
		d2=toDate.substring(0,2);
		
		//Converting to mm/dd/yyyy format
		fromDate= m1 + '/' + d1 + '/' + y1;
		toDate= m2 + '/' + d2 + '/' + y2;
		var fromDate1 = new Date(fromDate);		
		var toDate1 = new Date(toDate);	
		
			//alert("Current Date  :"+fromDate1+"   ,  Given Date : "+toDate1);
			diff = toDate1.getTime() - fromDate1.getTime();  
		    diff = Math.floor(diff / (1000 * 60 * 60 * 24) );
		   	
			// Checks if difference is more than or equalto zero then returns true		    
			if (diff >= 0) 
			{
				flag=true;
			} 
			// Otherwise returns false and sets focus to second date field			
			else 
			{
				//a.focus();
				flag=false;
			}
		
		return flag;
	}
	//Checking for valid alphabet with space	
	function isValidAlphabetsWithSpace(field)
	{
		sText=field.value;
		var flag=true;
		var validAlphabets = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		var char1;
		for(i = 0; i < sText.length ; i++) 

		{ 
      		char1 = sText.charAt(i); 
      		if (validAlphabets.indexOf(char1) == -1) 
       	 	{
       		 flag = false;
         	}
   		}
   		return flag;
   	}
   	//Checking for valid alphaNumeric  with space	
	function isValidAlphaNumericWithSpace(field)
	{
		sText=field.value;
		var flag=true;
		var validAlphabets = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		
		var char1;
		for(i = 0; i < sText.length ; i++) 

		{ 
      		char1 = sText.charAt(i); 
      		if (validAlphabets.indexOf(char1) == -1) 
       	 	{
       		 flag = false;
         	}
   		}
   		return flag;
   	}
   	//Checking for valid alphaNumeric  with space	
	function isValidAlphaNumericWithOutSpace(field)
	{
		sText=field.value;
		var flag=true;
		var validAlphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		
		var char1;
		for(i = 0; i < sText.length ; i++) 

		{ 
      		char1 = sText.charAt(i); 
      		if (validAlphabets.indexOf(char1) == -1) 
       	 	{
       		 flag = false;
         	}
   		}
   		return flag;
   	}
   	//Common Name Validation for Corporation,Division,Depot,Busstand ,Counter, Place , Area,Pickup Point Masters
	function isValidName(field)
	{
		sText=field.value;
		var flag=true;
		var validAlphabets = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890()-_[]{};./";
		
		var char1;
		for(i = 0; i < sText.length ; i++) 

		{ 
      		char1 = sText.charAt(i); 
      		if (validAlphabets.indexOf(char1) == -1) 
       	 	{
       		 flag = false;
         	}
   		}
   		return flag;
   	}
   		//Function to Check whether the given date is less than the Current Date
	function currentDateAndEffectveDateCompare(effDate) 
	{
		//Getting Current Date
		var d = new Date();
		//Getting Current Day
		var curr_day = d.getDay();
		var curr_date = d.getDate();
		//Getting Current Month
		var curr_month = d.getMonth()+1;
		//Getting Current Year
		var curr_year = d.getFullYear();
		

		flag=true;

		toDate=trim(effDate);
		
		y1=curr_year;
		m1=curr_month;
		d1=curr_date;
		
		y2=toDate.substring(6,10);
		m2=toDate.substring(3,5);
		d2=toDate.substring(0,2);
		
		//Converting to mm/dd/yyyy format
		fromDate= m1 + '/' + d1 + '/' + y1;
		toDate= m2 + '/' + d2 + '/' + y2;
		
		var fromDate1 = new Date(fromDate);		
		var toDate1 = new Date(toDate);	
		
			//alert("Current Date  :"+fromDate1+"   ,  Given Date : "+toDate1);
			diff = toDate1.getTime() - fromDate1.getTime();
		    diff = Math.floor(diff / (1000 * 60 * 60 * 24) );
		   
		   	
			// Checks if difference is more than or equalto zero then returns true		    
			if (diff > 0) 
			{
				flag=true;
			} 
			// Otherwise returns false and sets focus to second date field			
			else 
			{
				//a.focus();
				flag=false;
			}
		
		return flag;
	}
   	
	