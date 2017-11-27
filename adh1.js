(() => {
  let showLoadingMessage = whichMessage => $(".loading." + whichMessage).removeClass("d-none");
  let hideLoadingMessage = whichMessage => $(".loading." + whichMessage).addClass("d-none");

  let jsonToString = json => JSON.stringify(json, null, 2);
  let xmlToString = xml => new XMLSerializer().serializeToString(xml);

  $("button.ensembl").click(() => {
    showLoadingMessage("ensembl");

    // Crib sheet: http://rest.ensembl.org/documentation/info/lookup
    $.getJSON("http://rest.ensembl.org/lookup/id/YOL086C?content-type=application/json;expand=1").done(response => {
      console.log("Ensembl", response);
      $("textarea.ensembl").val(jsonToString(response));
      hideLoadingMessage("ensembl");
    });
  });

  $("button.ncbi").click(() => {
    showLoadingMessage("ncbi");

    // Crib sheet: https://www.ncbi.nlm.nih.gov/books/NBK25500/#chapter1.Downloading_Document_Summaries
    // See also: https://www.ncbi.nlm.nih.gov/books/NBK25500/#chapter1.Downloading_Full_Records
    $.get("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=gene&id=854068").done(response => {
      console.log("NCBI", response);
      $("textarea.ncbi").val(xmlToString(response));
      hideLoadingMessage("ncbi");
    });
  });

  $("button.uniprot").click(() => {
    showLoadingMessage("uniprot");

    // Crib sheet: http://www.uniprot.org/help/api_retrieve_entries
    $.get("http://www.uniprot.org/uniprot/P08018.xml").done(response => {
      console.log("UniProt", response);
      $("textarea.uniprot").val(xmlToString(response));
      hideLoadingMessage("uniprot");
    });
  });

  $("button.sgd").click(() => {
    showLoadingMessage("sgd");

    // Crib sheet: These are for FlyMine but they use the same "engine." The only difference is the server and path.
    // - http://iodocs.apps.intermine.org/flymine/docs#/ws-quick-search/GET/search
    // - http://iodocs.apps.intermine.org/flymine/docs#/ws-data/GET/data/:type
    $.getJSON("https://yeastmine.yeastgenome.org/yeastmine/service/data/Gene?id=1012319").done(response => {
      console.log("SGD", response);
      $("textarea.sgd").val(jsonToString(response));
      hideLoadingMessage("sgd");
    });
  });

})();
