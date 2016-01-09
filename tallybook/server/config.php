<?php
error_reporting(0);
set_error_handler("__userErrorHandler");
register_shutdown_function("__shutdownHandler");
date_default_timezone_set('PRC');
function __errtype($errno) {
	$errortype = array (
		E_ERROR => 'Error',
		E_WARNING => 'Warning',
		E_PARSE => 'Parsing Error',
		E_NOTICE => 'Notice',
		E_CORE_ERROR => 'Core Error',
		E_CORE_WARNING => 'Core Warning',
		E_COMPILE_ERROR => 'Compile Error',
		E_COMPILE_WARNING => 'Compile Warning',
		E_USER_ERROR => 'User Error',
		E_USER_WARNING => 'User Warning',
		E_USER_NOTICE => 'User Notice',
		E_STRICT => 'Runtime Notice',
		E_RECOVERABLE_ERROR => 'Catchable Fatal Error'
		);
	return $errortype[$errno];
} 

function getRoot(){
	return dirname(__DIR__);
}

function __userErrorHandler($errno, $errmsg, $filename, $linenum, $vars) {
	global $webroot;
	$errtype = __errtype($errno);
	$dt = date("H:i:s");
	$err = "$dt $errtype : $errmsg in $filename on $linenum\r\n";
	$path =  getRoot() . "/myfolder/log/debug_" . date("Ymd") . ".log";
	error_log($err, 3, $path);
	//echo $err;
} 

function __shutdownHandler() { // will be called when php script ends.
	$lasterror = error_get_last();
	switch ($lasterror['type']) {
		case E_ERROR:
		case E_CORE_ERROR:
		case E_COMPILE_ERROR:
		case E_USER_ERROR:
		case E_RECOVERABLE_ERROR:
		case E_CORE_WARNING:
		case E_COMPILE_WARNING:
		case E_PARSE:
			__userErrorHandler($lasterror['type'], $lasterror['message'], $lasterror['file'], $lasterror['line'], null);
	} 
} 
?>