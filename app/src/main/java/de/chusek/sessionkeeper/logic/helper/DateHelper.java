package de.chusek.sessionkeeper.logic.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Diese Klasse gibt uns verschiede
 * Daten zurueck.
 * Created by Alfa-Dozent on 03.02.2017.
 *
 * this Helper just helps and does not implement project requirements, it is use as library
 *
 */

public class DateHelper {

	private static final String SHORT_DATE_FORMATE_EU      = "dd.MM.yyyy";
	private static final String SHORT_DATE_FORMATE_US      = "yyyy.dd.MM";
	private static final String CURRENT_TIME_STAMP_FORMATE = "yyyy.dd.MM_HH.mm.ss";

	private static final int CURRENT_DATE_PLUS_A_WEEK = 7;

	private static DateFormat currentDateFormat;
	private static Date       currentDate;

	/**
	 * Gibt das aktuelle Datum
	 * in der deutschen Formatierung
	 * zurueck.
	 *
	 * @return strCurrentDate : {@link String} : Aktuelles Datum
	 */
	public static String getCurrentDate(boolean usStandard) {

		//Datumsformat festlegen
		if (!usStandard) {
			currentDateFormat = new SimpleDateFormat(SHORT_DATE_FORMATE_EU);
		} else {
			currentDateFormat = new SimpleDateFormat(SHORT_DATE_FORMATE_US);
		}

		//Akutelle Zeit bestimmen
		currentDate = new Date();

		//Aktuelle Zeit formatieren
		String strCurrentDate = currentDateFormat.format(currentDate);

		return strCurrentDate;
	}

	/**
	 * Gibt den aktuellen Zeitstempel
	 * fuer das Loggen zurueck.
	 *
	 * @return strCurrentTimeStamp : {@link String} : Akuteller Zeitstempel
	 */
	public static String getCurrentTimeStamp() {
		//Datumsformat festlegen
		currentDateFormat = new SimpleDateFormat(CURRENT_TIME_STAMP_FORMATE);

		//Akutelle Zeit bestimmen
		currentDate = new Date();

		//Aktuelle Zeit formatieren
		String strCurrentTimeStamp = currentDateFormat.format(currentDate);

		return strCurrentTimeStamp;
	}

	/**
	 * Standard Ablaufdatum fuer {@link de.rkasper.rkcoupongenerator.model.Coupon}s
	 * EU-Standard
	 *
	 * @return Date als String
	 */
	public static String getDefaultExpireDate() {
		//Aktuelle Zeit
		Calendar cal = Calendar.getInstance();

		//Datumsformat festlegen
		currentDateFormat = new SimpleDateFormat(SHORT_DATE_FORMATE_EU);

		//Hochzaehlen des aktuellen Datums
		cal.add(Calendar.DAY_OF_MONTH, CURRENT_DATE_PLUS_A_WEEK);

		//Datum zurueck geben
		return currentDateFormat.format(cal.getTime());

	}


	/**
	 * Keine Objektinstanziierung
	 * von außen
	 */
	private DateHelper() {
		//Kein Objekt anlegen
	}
}
