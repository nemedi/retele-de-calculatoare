<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/catalog">
		<items>
			<xsl:for-each select="cd">
				<item>
					<xsl:attribute name="title">
						<xsl:value-of select="title" />
					</xsl:attribute>
					<xsl:attribute name="artist">
						<xsl:value-of select="artist" />
					</xsl:attribute>
				</item>
			</xsl:for-each>
		</items>
	</xsl:template>
</xsl:stylesheet>