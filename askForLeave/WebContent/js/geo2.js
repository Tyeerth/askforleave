var addressInit = function(_cmbProvince, _cmbCity, _cmbArea, _cmbStreet, defaultProvince, defaultCity, defaultArea,defaultStreet)  
{  
    var cmbProvince = document.getElementById(_cmbProvince);  
    var cmbCity = document.getElementById(_cmbCity);  
    var cmbArea = document.getElementById(_cmbArea); 
	var cmbStreet = document.getElementById(_cmbStreet);
      
    function cmbSelect(cmb, str)  
    {  
        for(var i=0; i<cmb.options.length; i++)  
        {  
            if(cmb.options[i].value == str)  
            {  
                cmb.selectedIndex = i;  
                return;  
            }  
        }  
    }  
    function cmbAddOption(cmb, str, obj)  
    {  
        var option = document.createElement("OPTION");  
        cmb.options.add(option);  
        option.innerText = str;  
        option.value = str;  
        option.obj = obj;  
    }  
      
    function changeCity()  
    {  
        cmbArea.options.length = 0;  
        if(cmbCity.selectedIndex == -1)return;  
        var item = cmbCity.options[cmbCity.selectedIndex].obj;  
        for(var i=0; i<item.areaList.length; i++)  
        {  
            cmbAddOption(cmbArea, item.areaList[i], null);  
        }  
        cmbSelect(cmbArea, defaultArea);  
    }  
    function changeProvince()  
    {  
        cmbCity.options.length = 0;  
        cmbCity.onchange = null;  
        if(cmbProvince.selectedIndex == -1)return;  
        var item = cmbProvince.options[cmbProvince.selectedIndex].obj;  
        for(var i=0; i<item.cityList.length; i++)  
        {  
            cmbAddOption(cmbCity, item.cityList[i].name, item.cityList[i]);  
        }  
        cmbSelect(cmbCity, defaultCity);  
        changeCity();  
        cmbCity.onchange = changeCity;  
    }  
      
    for(var i=0; i<provinceList.length; i++)  
    {  
        cmbAddOption(cmbProvince, provinceList[i].name, provinceList[i]);  
    }  
    cmbSelect(cmbProvince, defaultProvince);  
    changeProvince();  
    cmbProvince.onchange = changeProvince;  
}  
  
var provinceList = [
{name:'请选择省份', cityList:[           
{name:'请选择城市', areaList:['请选择区县']}              
]},     
 
{name:'西藏', cityList:[           
{name:'拉萨市', areaList:['请选择区县']},          
{name:'昌都市', areaList:['请选择区县']},            
{name:'山南市', areaList:['请选择区县']},          
{name:'日喀则市', areaList:['桑珠孜区','谢通门县','非桑珠谢通门']},          
{name:'那曲市', areaList:['请选择区县']},           
{name:'阿里地区', areaList:['请选择区县']},             
{name:'林芝市', areaList:['请选择区县']}  
]}, 
{name:'非西藏', cityList:[           
	{name:'区', areaList:['县']},          
]}
]; 