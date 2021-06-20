
/* First created by JCasGen Mon May 24 22:13:39 CEST 2021 */
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
public class PredictedSentiment_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = PredictedSentiment.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.uni.due.haring.annotation.PredictedSentiment");
 
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
      jcas.throwFeatMissing("coveredText", "de.uni.due.haring.annotation.PredictedSentiment");
    return ll_cas.ll_getStringValue(addr, casFeatCode_coveredText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCoveredText(int addr, String v) {
        if (featOkTst && casFeat_coveredText == null)
      jcas.throwFeatMissing("coveredText", "de.uni.due.haring.annotation.PredictedSentiment");
    ll_cas.ll_setStringValue(addr, casFeatCode_coveredText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Sentiment;
  /** @generated */
  final int     casFeatCode_Sentiment;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSentiment(int addr) {
        if (featOkTst && casFeat_Sentiment == null)
      jcas.throwFeatMissing("Sentiment", "de.uni.due.haring.annotation.PredictedSentiment");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Sentiment);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSentiment(int addr, String v) {
        if (featOkTst && casFeat_Sentiment == null)
      jcas.throwFeatMissing("Sentiment", "de.uni.due.haring.annotation.PredictedSentiment");
    ll_cas.ll_setStringValue(addr, casFeatCode_Sentiment, v);}
    
  
 
  /** @generated */
  final Feature casFeat_NegativeSentiment;
  /** @generated */
  final int     casFeatCode_NegativeSentiment;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getNegativeSentiment(int addr) {
        if (featOkTst && casFeat_NegativeSentiment == null)
      jcas.throwFeatMissing("NegativeSentiment", "de.uni.due.haring.annotation.PredictedSentiment");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_NegativeSentiment);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNegativeSentiment(int addr, boolean v) {
        if (featOkTst && casFeat_NegativeSentiment == null)
      jcas.throwFeatMissing("NegativeSentiment", "de.uni.due.haring.annotation.PredictedSentiment");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_NegativeSentiment, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public PredictedSentiment_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_coveredText = jcas.getRequiredFeatureDE(casType, "coveredText", "uima.cas.String", featOkTst);
    casFeatCode_coveredText  = (null == casFeat_coveredText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_coveredText).getCode();

 
    casFeat_Sentiment = jcas.getRequiredFeatureDE(casType, "Sentiment", "uima.cas.String", featOkTst);
    casFeatCode_Sentiment  = (null == casFeat_Sentiment) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Sentiment).getCode();

 
    casFeat_NegativeSentiment = jcas.getRequiredFeatureDE(casType, "NegativeSentiment", "uima.cas.Boolean", featOkTst);
    casFeatCode_NegativeSentiment  = (null == casFeat_NegativeSentiment) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_NegativeSentiment).getCode();

  }
}



    