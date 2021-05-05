

/* First created by JCasGen Sun Feb 21 17:15:08 CET 2021 */
package webanno.custom;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed May 05 23:22:10 CEST 2021
 * XML source: C:/Users/Stewie/eclipse-workspace/hatespeech-sentiment-experiments/src/main/resources/set-1/TypeSystem.xml
 * @generated */
public class Zielgruppenadressierung extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Zielgruppenadressierung.class);
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
  protected Zielgruppenadressierung() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Zielgruppenadressierung(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Zielgruppenadressierung(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Zielgruppenadressierung(JCas jcas, int begin, int end) {
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
  //* Feature: GroupIndividual

  /** getter for GroupIndividual - gets 
   * @generated
   * @return value of the feature 
   */
  public String getGroupIndividual() {
    if (Zielgruppenadressierung_Type.featOkTst && ((Zielgruppenadressierung_Type)jcasType).casFeat_GroupIndividual == null)
      jcasType.jcas.throwFeatMissing("GroupIndividual", "webanno.custom.Zielgruppenadressierung");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Zielgruppenadressierung_Type)jcasType).casFeatCode_GroupIndividual);}
    
  /** setter for GroupIndividual - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGroupIndividual(String v) {
    if (Zielgruppenadressierung_Type.featOkTst && ((Zielgruppenadressierung_Type)jcasType).casFeat_GroupIndividual == null)
      jcasType.jcas.throwFeatMissing("GroupIndividual", "webanno.custom.Zielgruppenadressierung");
    jcasType.ll_cas.ll_setStringValue(addr, ((Zielgruppenadressierung_Type)jcasType).casFeatCode_GroupIndividual, v);}    
   
    
  //*--------------*
  //* Feature: GroupAffiliation

  /** getter for GroupAffiliation - gets 
   * @generated
   * @return value of the feature 
   */
  public String getGroupAffiliation() {
    if (Zielgruppenadressierung_Type.featOkTst && ((Zielgruppenadressierung_Type)jcasType).casFeat_GroupAffiliation == null)
      jcasType.jcas.throwFeatMissing("GroupAffiliation", "webanno.custom.Zielgruppenadressierung");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Zielgruppenadressierung_Type)jcasType).casFeatCode_GroupAffiliation);}
    
  /** setter for GroupAffiliation - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGroupAffiliation(String v) {
    if (Zielgruppenadressierung_Type.featOkTst && ((Zielgruppenadressierung_Type)jcasType).casFeat_GroupAffiliation == null)
      jcasType.jcas.throwFeatMissing("GroupAffiliation", "webanno.custom.Zielgruppenadressierung");
    jcasType.ll_cas.ll_setStringValue(addr, ((Zielgruppenadressierung_Type)jcasType).casFeatCode_GroupAffiliation, v);}    
   
    
  //*--------------*
  //* Feature: NegativeSentiment

  /** getter for NegativeSentiment - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getNegativeSentiment() {
    if (Zielgruppenadressierung_Type.featOkTst && ((Zielgruppenadressierung_Type)jcasType).casFeat_NegativeSentiment == null)
      jcasType.jcas.throwFeatMissing("NegativeSentiment", "webanno.custom.Zielgruppenadressierung");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Zielgruppenadressierung_Type)jcasType).casFeatCode_NegativeSentiment);}
    
  /** setter for NegativeSentiment - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNegativeSentiment(boolean v) {
    if (Zielgruppenadressierung_Type.featOkTst && ((Zielgruppenadressierung_Type)jcasType).casFeat_NegativeSentiment == null)
      jcasType.jcas.throwFeatMissing("NegativeSentiment", "webanno.custom.Zielgruppenadressierung");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Zielgruppenadressierung_Type)jcasType).casFeatCode_NegativeSentiment, v);}    
   
    
  //*--------------*
  //* Feature: ImplicitAddressing

  /** getter for ImplicitAddressing - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getImplicitAddressing() {
    if (Zielgruppenadressierung_Type.featOkTst && ((Zielgruppenadressierung_Type)jcasType).casFeat_ImplicitAddressing == null)
      jcasType.jcas.throwFeatMissing("ImplicitAddressing", "webanno.custom.Zielgruppenadressierung");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Zielgruppenadressierung_Type)jcasType).casFeatCode_ImplicitAddressing);}
    
  /** setter for ImplicitAddressing - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setImplicitAddressing(boolean v) {
    if (Zielgruppenadressierung_Type.featOkTst && ((Zielgruppenadressierung_Type)jcasType).casFeat_ImplicitAddressing == null)
      jcasType.jcas.throwFeatMissing("ImplicitAddressing", "webanno.custom.Zielgruppenadressierung");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Zielgruppenadressierung_Type)jcasType).casFeatCode_ImplicitAddressing, v);}    
  }

    