

/* First created by JCasGen Wed May 05 23:22:10 CEST 2021 */
package de.uni.due.haring.annotation;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed May 05 23:22:10 CEST 2021
 * XML source: C:/Users/Stewie/eclipse-workspace/hatespeech-sentiment-experiments/src/main/resources/set-1/TypeSystem.xml
 * @generated */
public class TwitterUser extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TwitterUser.class);
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
  protected TwitterUser() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public TwitterUser(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public TwitterUser(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public TwitterUser(JCas jcas, int begin, int end) {
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
    if (TwitterUser_Type.featOkTst && ((TwitterUser_Type)jcasType).casFeat_coveredText == null)
      jcasType.jcas.throwFeatMissing("coveredText", "de.uni.due.haring.annotation.TwitterUser");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TwitterUser_Type)jcasType).casFeatCode_coveredText);}
    
  /** setter for coveredText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCoveredText(String v) {
    if (TwitterUser_Type.featOkTst && ((TwitterUser_Type)jcasType).casFeat_coveredText == null)
      jcasType.jcas.throwFeatMissing("coveredText", "de.uni.due.haring.annotation.TwitterUser");
    jcasType.ll_cas.ll_setStringValue(addr, ((TwitterUser_Type)jcasType).casFeatCode_coveredText, v);}    
  }

    