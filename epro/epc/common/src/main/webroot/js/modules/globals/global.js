/**
 * 
 */
define( [ 
          'modules/globals/cores/browser',
          'modules/globals/cores/familyJoin',
          'modules/globals/cores/login',
          'modules/globals/cores/logout',
          'modules/globals/cores/noMemberLogin',
          'modules/globals/cores/noMemberOrder',
          'modules/globals/cores/b2eLogout',
          'modules/globals/cores/integrationChangeUser',
          'modules/globals/cores/isLogin',
          'modules/globals/cores/isMember',
          'modules/globals/cores/goHome',
          'modules/globals/cores/goBasket',
          'modules/globals/cores/goMyOrder',
          'modules/globals/cores/goWish',
          'modules/globals/cores/addBasket'
      ], function( 
    		  browser, 
    		  familyJoin, 
    		  login, 
    		  logout, 
    		  noMemberLogin,
    		  noMemberOrder, 
    		  b2eLogout,
    		  integrationChangeUser,
    		  isLogin,
    		  isMember,
    		  goHome,
    		  goBasket,
    		  goMyOrder,
    		  goWish,
    		  addBasket
      ) {
	console.log( 'global.js' );
		return {
			Browser : browser,
			familyJoin : familyJoin,
			login : login,
			logout : logout,
			noMemberLogin : noMemberLogin,
			noMemberOrder : noMemberOrder,
			b2eLogout : b2eLogout,
			integrationChangeUser : integrationChangeUser,
			isLogin : isLogin,
			isMember : isMember,
			goHome : goHome,
			goBasket : goBasket,
			goMyOrder : goMyOrder,
			goWish : goWish,
			addBasket : addBasket
		};
});