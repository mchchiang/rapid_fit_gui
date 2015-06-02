<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" 
              encoding="UTF-8"
              indent="yes"/>
	<xsl:strip-space elements="*"/>
	
	<!-- Identity Transform -->
	<xsl:template match="/|@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="Observable">
		<xsl:copy>
			<xsl:apply-templates select="*[name()!='Value']" />
			<xsl:variable name="noBracketString" select="substring($Value,2,length($Value)-1)"/>
			<xsl:for-each select="tokenize($noBracketString,',')">
				<Value>
					<xsl:value-of select="."/>
				</Value>			
			</xsl:for-each>
		</xsl:copy>
	</xsl:template>
	
</xsl:stylesheet>