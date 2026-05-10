<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:str="http://exslt.org/strings"
	xmlns:java="java://com.example.xslt.FlightsHelper"
	exclude-result-prefixes="str java">
	<xsl:param name="processedBy"/>
	<xsl:param name="processedAt"/>
	<xsl:output method="xml" />
	<xsl:template match="/airlines">
		<flights>
			<xsl:attribute name="processedBy">
				<xsl:value-of select="$processedBy"/>
			</xsl:attribute>
			<xsl:attribute name="processedAt">
				<xsl:value-of select="$processedAt"/>
			</xsl:attribute>
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
