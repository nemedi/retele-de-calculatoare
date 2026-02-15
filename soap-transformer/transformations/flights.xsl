<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:str="http://exslt.org/strings"
	xmlns:java="java://demo.FlightsHelper"
	exclude-result-prefixes="str java">
	<xsl:output method="xml" />
	<xsl:template match="/airlines">
		<flights>
			<xsl:for-each select="airline">
	      		<xsl:variable name="flights" select="java:getFlightsByAirline(@code)"/>
      			<xsl:for-each select="str:tokenize($flights, ',')">
					<xsl:variable name="flight" select="java:getFlightDetails(.)"/>
					<xsl:value-of select="$flight" disable-output-escaping="yes" />
      			</xsl:for-each>
			</xsl:for-each>
		</flights>
	</xsl:template>
</xsl:stylesheet>
