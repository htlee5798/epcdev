	var message_height = new Array();
	var message_count  = new Array();
	var scroll_count   = new Array();
	var timer_id       = new Array();
	var top_id         = new Array();
	var time_offset    = new Array();
	
	function scroll_up(roll_id) {
		var top = parseInt(document.getElementById("scroll"+roll_id).style.top);
		
		if(top%message_height[roll_id] == message_height[roll_id]*(-1)+1) {
			var tmp = top + message_height[roll_id] * message_count[roll_id] + message_height[roll_id] * scroll_count[roll_id] * 2 + message_height[roll_id];
			document.getElementById("scroll"+roll_id+top_id[roll_id]).style.top = tmp;
			if (top_id[roll_id] == (message_count[roll_id]-1)) { top_id[roll_id] = 0; } else { top_id[roll_id]++; }
			
			scroll_count[roll_id]++;
			if(scroll_count[roll_id]%1 == 0) { scroll_pause(roll_id); }
		}
		
		top -= 1;
		document.getElementById("scroll"+roll_id).style.top = top;
	}
	
	function scroll_rerun(roll_id) {
		clearInterval(timer_id[roll_id]);
		scroll_run(roll_id);
	}
	
	function scroll_pause(roll_id) {
		scroll_stop(roll_id);
		timer_id[roll_id] = setInterval("scroll_rerun('"+roll_id+"')", time_offset[roll_id]);
	}
	
	function scroll_run(roll_id) {
		timer_id[roll_id] = setInterval("scroll_up('"+roll_id+"')", 15);
	}
	
	function scroll_stop(roll_id) {
		clearInterval(timer_id[roll_id]);
	}