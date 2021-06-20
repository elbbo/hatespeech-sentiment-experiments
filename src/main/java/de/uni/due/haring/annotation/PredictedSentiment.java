

/* First created by JCasGen Mon May 24 22:13:39 CEST 2021 */
package de.uni.due.haring.annotation;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon May 24 22:13:39 CEST 2021
 * XML source: C:/Users/Stewie/eclipse-workspace/hatespeech-sentiment-experiments/src/main/resources/set-1/TypeSystem.xml
 * @generated */
public class PredictedSentiment extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PredictedSentiment.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected PredictedSentiment() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public PredictedSentiment(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public PredictedSentiment(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public PredictedSentiment(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: coveredText

  /** getter for coveredText - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCoveredText() {
    if (PredictedSentiment_Type.featOkTst && ((PredictedSentiment_Type)jcasType).casFeat_coveredText == null)
      jcasType.jcas.throwFeatMissing("coveredText", "de.uni.due.haring.annotation.PredictedSentiment");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PredictedSentiment_Type)jcasType).casFeatCode_coveredText);}
    
  /** setter for coveredText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCoveredText(String v) {
    if (PredictedSentiment_Type.featOkTst && ((PredictedSentiment_Type)jcasType).casFeat_coveredText == null)
      jcasType.jcas.throwFeatMissing("coveredText", "de.uni.due.haring.annotation.PredictedSentiment");
    jcasType.ll_cas.ll_setStringValue(addr, ((PredictedSentiment_Type)jcasType).casFeatCode_coveredText, v);}    
   
    
  //*--------------*
  //* Feature: Sentiment

  /** getter for Sentiment - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSentiment() {
    if (PredictedSentiment_Type.featOkTst && ((PredictedSentiment_Type)jcasType).casFeat_Sentiment == null)
      jcasType.jcas.throwFeatMissing("Sentiment", "de.uni.due.haring.annotation.PredictedSentiment");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PredictedSentiment_Type)jcasType).casFeatCode_Sentiment);}
    
  /** setter for Sentiment - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentiment(String v) {
    if (PredictedSentiment_Type.featOkTst && ((PredictedSentiment_Type)jcasType).casFeat_Sentiment == null)
      jcasType.jcas.throwFeatMissing("Sentiment", "de.uni.due.haring.annotation.PredictedSentiment");
    jcasType.ll_cas.ll_setStringValue(addr, ((PredictedSentiment_Type)jcasType).casFeatCode_Sentiment, v);}    
   
    
  //*--------------*
  //* Feature: NegativeSentiment

  /** getter for NegativeSentiment - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getNegativeSentiment() {
    if (PredictedSentiment_Type.featOkTst && ((PredictedSentiment_Type)jcasType).casFeat_NegativeSentiment == null)
      jcasType.jcas.throwFeatMissing("NegativeSentiment", "de.uni.due.haring.annotation.PredictedSentiment");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PredictedSentiment_Type)jcasType).casFeatCode_NegativeSentiment);}
    
  /** setter for NegativeSentiment - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNegativeSentiment(boolean v) {
    if (PredictedSentiment_Type.featOkTst && ((PredictedSentiment_Type)jcasType).casFeat_NegativeSentiment == null)
      jcasType.jcas.throwFeatMissing("NegativeSentiment", "de.uni.due.haring.annotation.PredictedSentiment");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PredictedSentiment_Type)jcasType).casFeatCode_NegativeSentiment, v);}    
  }

    