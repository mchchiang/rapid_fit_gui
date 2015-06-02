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
			<Value>
				<xsl:call-template name="join">
					<xsl:with-param name="list" select="Value" />
					<xsl:with-param name="separator" select="','" />
				</xsl:call-template>
			</Value>
		</xsl:copy>
	</xsl:template>
	
	<!-- for concatenating a list of values -->
	<xsl:template name="join">
		<xsl:param name="list" />
		<xsl:param name="separator" />

		<xsl:for-each select="$list">
			<xsl:if test="position() = '1'">
				<xsl:value-of select="'['" />
			</xsl:if>
			<xsl:value-of select="." />
			<xsl:if test="position() != last()">
				<xsl:value-of select="$separator" />
			</xsl:if>
			<xsl:if test="position() = last()">
				<xsl:value-of select="']'" />
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>