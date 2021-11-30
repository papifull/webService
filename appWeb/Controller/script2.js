
	function Confirm(va1) {
		var rettVal = confirm("Voulez vous supprimer cet article?");
		if (rettVal == true) {
			var va=va1;
			document.location.href="http://localhost/appWeb/Modele/supprimer.php?id="+va;
			return true;
		}
		else{
			return false;
		}
	}

	function Modif(va1) {
		var rettVal = confirm("Voulez vous modifier cet article?");
		if (rettVal == true) {
			var va=va1;
			document.location.href="http://localhost/appWeb/Modele/modifier.php?idd="+va;
			return true;
		}
		else{
			return false;
		}
	}
