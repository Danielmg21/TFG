/*:
*
* @plugindesc This is my first Plugin!
* The break Line
* @author Daniel
*
*@help
*help file
*/

(function(){

	Scene_Title.prototype.drawGameTitle = function() {
    const x = 20;
    const y = Graphics.height / 4;
    const maxWidth = Graphics.width - x * 2;
    const text = $dataSystem.gameTitle;
    const bitmap = this._gameTitleSprite.bitmap;
    bitmap.fontFace = $gameSystem.mainFontFace();
    bitmap.outlineColor = "blue";
    bitmap.outlineWidth = 21;
    bitmap.fontSize = 88;
    bitmap.drawText(text, x, y, maxWidth, 48, "center");
};
})();