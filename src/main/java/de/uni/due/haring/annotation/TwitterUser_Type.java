
/* First created by JCasGen Wed May 05 23:22:10 CEST 2021 */
package de.uni.due.haring.annotation;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Mon May 24 22:13:39 CEST 2021
 * @generated */
public class TwitterUser_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TwitterUser.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.uni.due.haring.annotation.TwitterUser");
 
  /** @generated */
  final Feature casFeat_coveredText;
  /** @generated */
  final int     casFeatCode_coveredText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCoveredText(int addr) {
        if (featOkTst && casFeat_coveredText == null)
      jcas.throwFeatMissing("coveredText", "de.uni.due.haring.annotation.TwitterUser");
    return ll_cas.ll_getStringValue(addr, casFeatCode_coveredText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCoveredText(int addr, String v) {
        if (featOkTst && casFeat_coveredText == null)
      jcas.throwFeatMissing("coveredText", "de.uni.due.haring.annotation.TwitterUser");
    ll_cas.ll_setStringValue(addr, casFeatCode_coveredText, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public TwitterUser_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_coveredText = jcas.getRequiredFeatureDE(casType, "coveredText", "uima.cas.String", featOkTst);
    casFeatCode_coveredText  = (null == casFeat_coveredText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_coveredText).getCode();

  }
}



    