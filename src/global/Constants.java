package global;

import javax.swing.ImageIcon;

import shapes.TLine;
import shapes.TOval;
import shapes.TPen;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TShape;
import shapes.TText;
import shapes.TSelection;


public class Constants {
	
	public enum ETransformationStyle{
		e2PTransformation,
		eNPTransformation, 
		eErase, 
		ePaint, 
		eFill
	}

	public enum ETools{
		eSelection("선택", new TSelection(), new ImageIcon("icon/unselectedPointer.png"), new ImageIcon("icon/selectedPointer.png"), ETransformationStyle.e2PTransformation),
		eRectangle("사각형", new TRectangle(), new ImageIcon("icon/unselectedRectangle.png"), new ImageIcon("icon/selectedRectangle.png"), ETransformationStyle.e2PTransformation),
		eOval("원 ", new TOval(), new ImageIcon("icon/unselectedOval.png"), new ImageIcon("icon/selectedOval.png"), ETransformationStyle.e2PTransformation),
		eLine("직선", new TLine(), new ImageIcon("icon/unselectedLine.png"), new ImageIcon("icon/selectedLine.png"), ETransformationStyle.e2PTransformation),
		ePolygon("다각형", new TPolygon(), new ImageIcon("icon/unselectedPolygon.png"), new ImageIcon("icon/selectedPolygon.png"), ETransformationStyle.eNPTransformation),
		ePen("펜", new TPen(), new ImageIcon("icon/unselectedPen.png"), new ImageIcon("icon/selectedPen.png"), ETransformationStyle.e2PTransformation),
		eText("텍스트", new TText(), new ImageIcon("icon/unselectedText.png"), new ImageIcon("icon/selectedText.png"), ETransformationStyle.e2PTransformation),
		eEraser("지우개", new TText(), new ImageIcon("icon/unselectedEraser.png"), new ImageIcon("icon/selectedEraser.png"), ETransformationStyle.eErase),
		ePaint("라인 색", new TText(), new ImageIcon("icon/unselectedPaint.png"), new ImageIcon("icon/selectedPaint.png"), ETransformationStyle.ePaint),
		eFill("도형 채우기", new TText(), new ImageIcon("icon/unselectedFill.png"), new ImageIcon("icon/selectedFill.png"), ETransformationStyle.eFill);
		
		private String label;
		private TShape tool;
		private ImageIcon unselectedIcon, selectedIcon;
		private ETransformationStyle eTransformationStyle;
		
		private ETools(String label, TShape tool, ImageIcon unselectedIcon, ImageIcon selectedIcon, ETransformationStyle eTransformationStyle) {
			this.label = label;
			this.tool = tool;
			this.unselectedIcon = unselectedIcon;
			this.selectedIcon = selectedIcon;
			this.eTransformationStyle = eTransformationStyle;
		}
		
		public TShape newShape() {
			return this.tool.newTShape();
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public ImageIcon getUnselectdIcon() {
			return this.unselectedIcon;
		}
		
		public ImageIcon getSelectdIcon() {
			return this.selectedIcon;
		}

		public  ETransformationStyle getTransformationStyle() {
			// TODO Auto-generated method stub
			return this.eTransformationStyle;
		}

	}
	
	public enum EFileMenu{
		eNew("새로 만들기", 'N'),
		eOpen("열기", 'O'),
		eSave("저장", 'S'),
		eSaveAs("다른이름으로 저장", 'A'),
		ePrint("프린트", 'P'),
		eQuit("종료", 'Q');
		
		private String label;
		private char ShortcutKey;

		private EFileMenu(String label, char ShortcutKey) {
			this.label = label;		
			this.ShortcutKey = ShortcutKey;
		}
		public String getLabel() {
			return this.label;
		}
		public char getShortcutKey() {
			return this.ShortcutKey;
		}
	}
	
	public enum EEditMenu{
		eUndo("취소", 'Z'),
		eRedo("되돌리기", 'Y'),
		eCut("잘라내기", 'X'),
		eCopy("복사", 'C'),
		ePaste("붙여넣기", 'V');
		
		private String label;
		private char ShortcutKey;

		private EEditMenu(String label, char ShortcutKey) {
			this.label = label;
			this.ShortcutKey = ShortcutKey;

		
		}
		public String getLabel() {
			return this.label;
		}
		
		public char getShortcutKey() {
			return this.ShortcutKey;
		}
	}
	



}
