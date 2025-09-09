
Map = function(){
	  this.map = new Object();
	 };   
	 Map.prototype = {   
	     put : function(key, value){   
	         this.map[key] = value;
	     },
	     putMap : function(key, value){
	      this.map[key] = value.map;
	     },
	     putMapList : function(key, value){
	      var list = new Array();
	      for(var i=0;i<value.length;i++){
	       list.push(value[i].map);
	      }
	      this.map[key] = list;
	     },
	     get : function(key){   
	         return this.map[key];
	     },
	     containsKey : function(key){    
	      return key in this.map;
	     },
	     containsValue : function(value){    
	      for(var prop in this.map){
	       if(this.map[prop] == value) return true;
	      }
	      return false;
	     },
	     isEmpty : function(key){    
	      return (this.size() == 0);
	     },
	     clear : function(){   
	      for(var prop in this.map){
	       delete this.map[prop];
	      }
	     },
	     remove : function(key){    
	      delete this.map[key];
	     },
	     keys : function(){
	         var keys = new Array();
	         for(var prop in this.map){
	             keys.push(prop);
	         }
	         return keys;
	     },
	     values : function(){   
	      var values = new Array();   
	         for(var prop in this.map){   
	          values.push(this.map[prop]);
	         }   
	         return values;
	     },
	    size : function() {
	     var count = 0;
	     for (var prop in this.map) {
	       count++;
	     }
	     return count;
	    },
	     jsonString: function(){
	      return JSON.stringify(this.map);    
	     },
	    toQueryString: function(divMark)
	    {
	        var divMark = (typeof divMark == "undefined") ? "&" : divMark;
	        var quaryString = "";
	        var key = this.keys();
	        var value = this.values();
	        if ( this.size < 1 ) return "";
	        for( var i = 0 ; i < this.size ; i++ )
	        {
	            if ( quaryString != "" )
	                quaryString += divMark;

	            quaryString +=     this.key[i] +"="+ value[i];
	        }
	        return quaryString;
	    }
	 };
	 
var JHashMap = function()
{
    this.obj = [];
    this.length = 0;        
    this.put = function(key, value)
    { 
        if( this.obj[key] == null )this.length++; 
        this.obj[key] = value; 
    };

    this.get = function(key)
    {
        return this.obj[key];
    };

    this.keys = function()
    {
        var keys = [];
        for ( var property in this.obj ) keys.push(property);
        return keys;
    };

    this.values = function()
    {
        var values = [];
        for ( var property in this.obj ) values.push(this.obj[property]);
        return values;
    };

    this.toQueryString = function(divMark)
    {
        var divMark = (typeof divMark == "undefined") ? "&" : divMark;
        var quaryString = "";
        var key = this.keys();
        var value = this.values();
        if ( this.length < 1 ) return "";

        for( var i = 0 ; i < this.length ; i++ )
        {
            if ( quaryString != "" )
                quaryString += divMark;

            quaryString +=     key[i] +"="+ value[i];
        }
        return quaryString;
    };

    this.remove = function(index)
    {
        var keys = this.keys();
        keys.splice(index, 1);
        var temp =[];                 
        for ( var i = 0 ; i < keys.length ; i++ )
        {
            temp[keys[i]] = this.obj[keys[i]];
        }     

        this.obj = temp;
        this.length = keys.length;
        index--;
    };

    this.indexOf = function(key)
    {
        var cnt = 0;
        for ( var i in this.obj )
        {
            if ( key == i ) return cnt;
                cnt++;    
        }
    };

    this.splice = function(spliceIndex)
    {
        var keys = this.keys();
        keys.splice(spliceIndex, 1);
        var temp =[];                 
        for ( var i = 0 ; i < keys.length ; i++ )
        {
            temp[keys[i]]=this.obj[keys[i]];
        }     
        this.obj = temp;
        this.length = keys.length;
        index--;
    };

    this.point = function(key)
    {
        var cnt = 0;
        for ( var i in this.obj )
        {
            if ( key == i ) return cnt;
                cnt++;    
        }
    };

    this.clear = function()
    {
        this.obj = [];
        this.length = 0;
    };

    var index = 0;
    this.next = function()
    {
        if ( index == this.length )
        {
            index = 0;
            return -1;
        }
        var values = this.values();
        var currentValue = values[index];     
        index++;
        return currentValue;
    };

    this.indexValue = function(Idx)
    {
        var keys = this.keys();
        return this.obj[keys[Idx]];
    };
};



