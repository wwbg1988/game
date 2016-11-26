//姓名
function isNameValid(name)
{
	return !!name&&(name.length>0)&&(name.length<=16);
}

function isLengthBetweenAndEqual(str, min, max)
{
	return !!str&&(str.length>=min)&&(str.length<=max);
}


//手机号码验证
function isPhoneValid(tel)
{
	var telReg = !!tel.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/);
	//如果手机号码不能通过验证
	return telReg;
}

//1 date1>date2 yyyy-MM-dd s
//0 date1=date2
//-1 date1<date2
function dateCompareTo(date1, date2)
{
	var d1 = new Date(date1.replace(/-/g,"/")); 
	var d2 = new Date(date2.replace(/-/g,"/")); 
	var m = (d1.getTime()-d2.getTime())/(1000*60*60); 
	
	if(m > 0)
	{
		return 1;
	}
	else if(m < 0)
	{
		return -1;
	}
	else
	{
		return 0;
	}
}