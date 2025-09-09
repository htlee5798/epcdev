if (typeof NetFunnel == "object") {
	//skin1
	NetFunnel.SkinUtil.add('test', {
		updateCallback: function (percent, nwait, totwait, timeleft) {
			var progress_print = document.querySelector('.delay-bar .bar');
			progress_print.style.width = percent + "%";
		},
		htmlStr: '<div id="NetFunnel_Skin_Top" class="wrapper-cartdelay-layer active">' +
		'	<div class="wrap-cartdelay-layer"> ' +
		'		<div class="title">' +
		'			<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAWgAAAAuCAMAAAAyTXMiAAAAP1BMVEUAAAAAAAAREREREREICAgREREICAgREREREREREREAAAAREREREREREREREREREREAAAAAAAAREREAAAAREREnz89tAAAAFHRSTlMAM0S7D3ciM+7MGJlmiN1VCiqqB+69jVQAAATwSURBVHja7ZrZeqMwDIUdrxDWLO//rBOwkoNrB9ts0/mGc9NCjYR/hCSbMtL1yiaSmp3aQ/J2uUxI68vlxk4tkPyofx09cEjhfHnpQ7p/UT9JL5K8fKSJK0naAB5kCHRv7MCendoUNB3qBziP1E9tDFrSedJt5HyG8/agbw7n68l5H9BElr01Jo4zb+wAWn/IYiQ7tT1oM/5wMscvXK3wl9KHGna44qBtC00tto3vmxyUNKnnS8yXeokly4iX2Kw8P0GfNQ1NNrZqHNwmg8afNA7lCtDO6Wwr+QYB+ADQvrvtQD8DmkEkh9NmKWitKiHuSh8N2tRKKb4YNJbg1MfREvy6I+huOF37QAPSP61w8XZw1ytBG8/ZjLG6tV6LSi8DDd0C/cVjF9DVE+4jVtQPK+oJFbUHOmxLhGcuPGdfAcq7NwzjUHWSQD9GfL3TX9xYGmhuNVLgpDBoe3ZUvQB09QxrZ9CydMZV7jj8HgcNsm58R0H7BKNlTBYUkCYbdGfnyYdEvQPo7hvoZrhdZRgzarx5tQJ072yFWqLmkQfaRnQEtCkH32K49Y4F9d1KO3k+Jgqav2USiqGAU8+YpuKN4NaLQffGCWhN2PNA3yfvH3IJczSGRCFtWDcyC3Q3XKqnj7UklEHQWV0HWPrXNkgXL9LtcONLQV8vNoIdzprlgbbwWnj2I60W76RhxsGF0qmgCa2COzhja0AjaAPXItoxB1jKBd1ry7kn6gacc0DXKHJB0KaxLVJpRk6CurRaJ4GmCU8GlzC+AjQ6zjLNmKRDpKck0Kh8t95JIzr0lavHIfta5GQANPpf5Iu6ePdpIh00g8QmoJEdmkRjAE1KAQ3SVydhG8lyVb2LXClDxbCl1kij65TqfZJZocqptIgu1oPG3XVzxsBDrwHNrg+nNF5ZjtBymJHSXTqIkFhKpX9MpasKoudybdJytNgENJVZOZejOzdH54HeUg3hUcjCQDRKdTqIwdS1/8TKEGhOhRTjoDhoyJ+5wGs103UgQ6p4MaSWaGPQWnzoVMgQy3bv7CpMx/vo7UBzlIWvfXQ96aMLHQcNbQfaWLilnEZatxC0xpLQA82dleF2oAVOfl0ZPr2V4eGgZQnOI46WMh4QSe5oDCBXTuYYr3dBIz9CFQyGQcffZbjkc2Hg7XUcDtrvE6ibUF52ndc0BRaVY2+KsSu8+a4uhraA4zh79+64YqgoJ0OdxEeoRNCIrgabC16ml4pQlx3bBrQp8ALl7UcfD5pJ9l1ZoI0thBVSEUBPP3XUhrFtQOtyrCi5X1iOBw2JlyKfVWOSrU0JugBpgPa1HrRBdUvqiX4DaNcZEGXo0zsplNfdQCPnV2xL0LyufzloWSG6Kix8YAU7sCmgaaxS9/LuzRwOwTlsDE7lPGilGiHGRCT+Kmj1/CYnW96dnrGRsBL0E++uvs+8a62PiDHsdM2AdrUzaF8ZoKUqkC5AWm0Cug3MnIv3J97/DHQLzliPlSwKOvwRGirEXQWSJqcuUbN80L7TtyshlOoM+6ugNQ8JIwzt+0G8LXQUdFicJMPFEF9cGcsGHdcBoF2KKqtfUP60NYuAXjFz2Q7LrH8V9Kr/mpPM046gmXlh/j9Bs8NAk07QJ+jMYrgT6LB2Bh2WOkGfoI8GDR0I2vlPtI3Fw4pv+WWC5hFHUYdNMui4U73quUJ/ADHS+BLE6IDjAAAAAElFTkSuQmCC" alt="접속지연 대기 안내">' +
		'			<p>현재 동시접속 이용자 증가로인해 대기중입니다.<br>잠시만 기다리시면 자동으로 이동됩니다.</p>' +
		'		</div>' +
		'		<div class="wrap-delay">' +
		'			<div class="delay-bar">' +
		'				<em class="bar" style="width:65%;"></em>' +
		'			</div>' +
		'			<strong class="my-count">내 대기순서 <span id="NetFunnel_Loading_Popup_Count" class="point1"></span>번째</strong>' +
		'			<span class="count">뒤에<em id="NetFunnel_Loading_Popup_NextCnt" class="point1"></em>명 / ' +
		'			<em id="NetFunnel_Loading_Popup_TimeLeft" style="color:#ed1c24"></em> 소요예상</span>' +
		'		</div>' +
		'		<div class="delay-info">' +
		'			<p>잠시만 기다리시면 안전하게 자동 접속됩니다.<br>새로고침 하시면 접속 순번이 뒤로 밀리니 주의하시기 바랍니다.</p>' +
		'		</div>' +
		'	</div> ' +
		'	<div class="layer-mask"></div>' +
		'</div>'
	}, 'normal');
}